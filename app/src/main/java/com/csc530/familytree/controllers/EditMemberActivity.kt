package com.csc530.familytree.controllers

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityEditMemberBinding
import com.csc530.familytree.models.Member
import com.csc530.familytree.models.Tree
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.util.*


class EditMemberActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityEditMemberBinding
	private val locale = Locale.getDefault();
	override fun onCreate(savedInstanceState: Bundle?)
	{
		val auth = FirebaseAuth.getInstance()
		super.onCreate(savedInstanceState)
		binding = ActivityEditMemberBinding.inflate(layoutInflater)
		setContentView(binding.root)
		//? show datepicker when birth or date date is selected
		binding.edtDD.setOnFocusChangeListener { deathDate, hasFocus ->
			deathDate.isEnabled = !hasFocus
			if(hasFocus)
				setDate(deathDate as EditText, "Death date")
		}
		binding.edtBD.setOnFocusChangeListener { birthDate, hasFocus ->
			birthDate.isEnabled = !hasFocus
			if(hasFocus)
				setDate(birthDate as EditText, "Birth date")
		}
		binding.btnCnfm.setOnClickListener {
			val firstName = binding.edtFName.text.toString()
			val lastName = binding.edtLName.text.toString()
			val birthdate = if(binding.edtBD.text.toString().isNotEmpty())
				LocalDate.parse(binding.edtBD.text.toString()).toEpochDay()
			else
				null
			val deathDate = if(binding.edtDD.text.toString().isNotEmpty())
				LocalDate.parse(binding.edtDD.text.toString()).toEpochDay()
			else
				null
			val comments = binding.taOther.text.toString()
			val member = Member(firstName, lastName, birthdate, deathDate, uid = auth.currentUser!!.uid)
			val intent = Intent(this, TreeActivity::class.java)
			//? write to db if logged in
			if(auth.currentUser != null)
			{
				val firebase = FirebaseFirestore.getInstance()
				val collection = firebase.collection("Trees")
				val docPath = this.intent.getStringExtra("docPath")!!
				firebase.document(docPath).get().addOnSuccessListener {
					println(it.toString())
					println(it.data.toString())
					val familyTree = it.toObject(Tree::class.javaObjectType)!!
					
					//Update last modified timestamp
					familyTree.lastModified = Timestamp.now()
					
					//? check if they are updating a predefined member in the tree
					val memberID = this.intent.getStringExtra("memberID")
					if(memberID != null)
					{
						val values = HashMap<String, Any?>()
						values["firstName"] = firstName
						values["lastName"] = lastName
						values["birthdate"] = birthdate
						values["deathdate"] = deathDate
						values["comments"] = comments
						for(field in values)
							collection.document(docPath)
								.update(FieldPath.of("members", field.key), field.value)
					}
					else
					{
						member.id = collection.document().id
						familyTree.members.add(member)
						firebase.document(docPath)
							.update("members", familyTree.members, "lastModified", familyTree.lastModified)
							.addOnFailureListener { e ->
								Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
								println(e)
							}
					}
					intent.putExtra("treeName", familyTree.name)
					startActivity(intent)
				}
			}
			else
			{
				//TODO: write to file if not logged in and read from file on scene change
				//			intent.putExtra("member", member)
			}
		}
	}
	
	private fun setDate(value: EditText, title: String)
	{
		val date = DatePickerDialog(this)
		date.updateDate(LocalDate.now().year, LocalDate.now().monthValue, LocalDate.now().dayOfMonth)
		date.setTitle(title)
		date.datePicker.maxDate = Date().toInstant().toEpochMilli()
		date.setButton(DialogInterface.BUTTON_POSITIVE, "Confirm") { _, _ ->
			val datepicker = date.datePicker
			val selectedDate = LocalDate.of(datepicker.year, datepicker.month, datepicker.dayOfMonth)
			value.setText(selectedDate.toString())
		}
		date.setButton(DialogInterface.BUTTON_NEUTRAL, "Clear") { _, _ ->
			value.text = null
		}
		date.setOnDismissListener { value.clearFocus() }
		date.show()
	}
}