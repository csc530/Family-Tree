package com.csc530.familytree.controllers

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.R
import com.csc530.familytree.databinding.ActivityEditMemberBinding
import com.csc530.familytree.models.ActivityManager
import com.csc530.familytree.models.FamilyMember
import com.csc530.familytree.models.FamilyTree
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.util.*


class EditMemberActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityEditMemberBinding
	private val locale = Locale.getDefault()
	private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()
	private val collection = firebase.collection("Trees")
	private val activityManager = ActivityManager(this)
	override fun onCreate(savedInstanceState: Bundle?)
	{
		val auth = FirebaseAuth.getInstance()
		super.onCreate(savedInstanceState)
		binding = ActivityEditMemberBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		val docPath = intent.getStringExtra("docPath") ?: return activityManager.backToHome(this)
		
		binding.btnCncl.setOnClickListener {
			val intent = Intent(this, TreeActivity::class.java)
			intent.putExtra("docPath", docPath)
			startActivity(intent)
		}
		
		//? setup parent spinners
		val fathers: ArrayList<FamilyMember> = ArrayList<FamilyMember>()
		val fatherAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, fathers)
		binding.spinDad.adapter = fatherAdapter
		val mothers = ArrayList<FamilyMember>()
		val motherAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, mothers)
		binding.spinMom.adapter = motherAdapter
		
		firebase.document(docPath).get().addOnSuccessListener {
			val tree = it.toObject(FamilyTree::class.java)
			val members = tree?.members
			if(members != null)
			{
				motherAdapter.add(FamilyMember("Select", "Mother", id = FamilyMember.NULL_ID))
				motherAdapter.addAll(members)
				fatherAdapter.add(FamilyMember("Select", "Father", id = FamilyMember.NULL_ID))
				fatherAdapter.addAll(members)
			}
		}
		
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
			val member = parseData(motherAdapter, fatherAdapter)
			
			//? write to db if logged in
			if(auth.currentUser != null)
				uploadToDB(member, docPath)
			else
			{
				//TODO: write to file if not logged in and read from file on scene change
				//			intent.putExtra("member", member)
			}
		}
	}
	
	private fun parseData(motherAdapter: ArrayAdapter<FamilyMember>, fatherAdapter: ArrayAdapter<FamilyMember>): FamilyMember
	{
		val firstName = binding.edtFName.text.toString()
		val lastName = binding.edtLName.text.toString()
		val birthdate =
				if(binding.edtBD.text.toString().isNotEmpty())
					LocalDate.parse(binding.edtBD.text.toString()).toEpochDay()
				else
					null
		val deathDate =
				if(binding.edtDD.text.toString().isNotEmpty())
					LocalDate.parse(binding.edtDD.text.toString()).toEpochDay()
				else
					null
		val mom =
				if(binding.spinMom.selectedItemPosition != Spinner.INVALID_POSITION && binding.spinMom.selectedItemPosition != 0)
					motherAdapter.getItem(binding.spinMom.selectedItemPosition)
				else
					null
		val dad =
				if(binding.spinDad.selectedItemPosition != Spinner.INVALID_POSITION && binding.spinDad.selectedItemPosition != 0)
					fatherAdapter.getItem(binding.spinDad.selectedItemPosition)
				else
					null
		
		val biography = binding.taOther.text.toString()
		val member = FamilyMember(firstName, lastName, birthdate, deathDate)
		if(mom?.id != null)
			member.mom = mom.id!!
		if(dad?.id != null)
			member.dad = dad.id!!
		return member
	}
	
	private fun uploadToDB(member: FamilyMember, docPath: String)
	{
		firebase.document(docPath).get().addOnSuccessListener {
			val familyTree = it.toObject(FamilyTree::class.javaObjectType)!!
			
			//Update last modified timestamp
			familyTree.lastModified = Timestamp.now()
			
			//? check if they are updating a predefined member in the tree
			val memberId = intent.getStringExtra("memberId")
			if(memberId != null)
			{
				val oldVersion = familyTree.findMemberByID(memberId)
				familyTree.members.remove(oldVersion)
				familyTree.members.add(member)
				firebase.document(docPath).update("members", familyTree.members,
				                                  "lastModified", familyTree.lastModified)
					.addOnFailureListener { e ->
						Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
						println(e)
					}
				//navigate back to member details
				activityManager.startActivity(EditMemberActivity::class.java, docPath, memberId)
			}
			else
			{
				member.id = collection.document().id
				familyTree.members.add(member)
				firebase.document(docPath)
					.update("members", familyTree.members,
					        "lastModified", familyTree.lastModified)
					.addOnFailureListener { e ->
						Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
						println(e)
					}
				//navigate back to family tree
				activityManager.startActivity(TreeActivity::class.java, docPath)
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
			if(datepicker.month !in 1..13 && datepicker.dayOfMonth < 1)
				return@setButton
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