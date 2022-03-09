package com.csc530.familytree.controllers

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityEditMemberBinding
import com.csc530.familytree.models.Member
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.util.*
import kotlin.collections.HashMap


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
			val birthdate = DateTime.parse(binding.edtBD.text.toString())
			val deathDate = DateTime.parse(binding.edtDD.text.toString())
			val comments = binding.taOther.text.toString()
			val member = Member(firstName, lastName, birthdate, deathDate)
			val intent = Intent(this, TreeActivity::class.java)
			// TODO:  find a way to transfer the new member information across intents
			//? write to db if logged in
			if(auth.currentUser != null)
			{
				val firebase = FirebaseFirestore.getInstance()
				val collection = firebase.collection("Trees")
				//? check if they are updating a predefined member in the tree
				val memberID = this.intent.getStringExtra("memberID")
				if(memberID != null)
				{
					val values = HashMap<String, Any>()
					values["firstName"] = firstName
					values["lastName"] = lastName
					values["birthdate"] = birthdate
					values["deathdate"] = deathDate
					values["comments"] = comments
					val query = collection.document().update(values)
				}
				else
					collection.add(member)
			}
			//TODO: write to file if not logged in and read from file on scene change
			//			intent.putExtra("member", member)
			startActivity(intent)
		}
	}
	
	private fun setDate(value: EditText, title: String)
	{
		val date = DatePickerDialog(this)
		date.updateDate(LocalDate.now().year, LocalDate.now().monthOfYear, LocalDate.now().dayOfMonth)
		date.setTitle(title)
		date.datePicker.maxDate = Date().toInstant().toEpochMilli()
		date.setButton(DialogInterface.BUTTON_POSITIVE, "Confirm") { _, _ ->
			val year = date.datePicker.year
			val month = date.datePicker.month
			val day = date.datePicker.dayOfMonth
			value.setText(org.joda.time.LocalDate.parse("$year-$month-$day").toString())
		}
		date.setButton(DialogInterface.BUTTON_NEUTRAL, "Clear") { _, _ ->
			value.text = null
		}
		date.setOnDismissListener {
			value.clearFocus()
		}
		date.show()
	}
}