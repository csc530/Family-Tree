package com.csc530.familytree.controllers

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.R
import com.csc530.familytree.databinding.ActivityEditMemberBinding
import com.csc530.familytree.models.ActivityManager
import com.csc530.familytree.models.FamilyMember
import com.csc530.familytree.models.FamilyTree
import com.csc530.familytree.models.SexEnum
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.util.*


class EditMemberActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityEditMemberBinding
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
		val memberId = intent.getStringExtra("memberId")
		
		// ? setup spinners
		val (fatherAdapter, motherAdapter) = setupParentSpinners(docPath, memberId)
		// * populate form with current member details if they are updating a member
		if(memberId != null)
		{
			firebase.document(docPath).get().addOnSuccessListener {
				val member = it.toObject(FamilyTree::class.java)?.findMemberByID(memberId)
				if(member == null)
					return@addOnSuccessListener finish()
				binding.txtEdtTitle.text = resources.getText(R.string.edit_member)
				binding.edtFName.setText(member.firstName)
				binding.edtLName.setText(member.lastName)
				binding.sexSpinner.setSelection(member.sex.ordinal)
				if(member.getBirthDate() != null)
					binding.edtBirthDate.setText(member.getBirthDate().toString())
				if(member.getDeathDate() != null)
					binding.edtDeathDate.setText(member.getDeathDate().toString())
				binding.taOther.setText(member.biography)
			}
		}
		else
			binding.txtEdtTitle.text = resources.getText(R.string.add_member)
		
		binding.btnCncl.setOnClickListener { finish() }
		
		//? show datepicker when birth or date date is selected
		binding.edtDeathDate.setOnFocusChangeListener { deathDate, hasFocus ->
			deathDate.isEnabled = !hasFocus
			if(hasFocus)
				setDate(deathDate as EditText, "Death date")
		}
		binding.edtBirthDate.setOnFocusChangeListener { birthDate, hasFocus ->
			birthDate.isEnabled = !hasFocus
			if(hasFocus)
				setDate(birthDate as EditText, "Birth date")
		}
		
		binding.btnCnfm.setOnClickListener {
			val member = parseData(motherAdapter, fatherAdapter)
			
			//? write to db if logged in
			if(auth.currentUser != null)
				uploadToDB(member, docPath, memberId)
			else
			{
				//TODO: write to file if not logged in and read from file on scene change
				//			intent.putExtra("member", member)
			}
		}
	}
	
	private fun setupParentSpinners(docPath: String, memberId: String?): Pair<ArrayAdapter<FamilyMember>, ArrayAdapter<FamilyMember>>
	{
		//? setup parent spinners
		val fathers: ArrayList<FamilyMember> = ArrayList<FamilyMember>()
		val fatherAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, fathers)
		binding.spinDad.adapter = fatherAdapter
		val mothers = ArrayList<FamilyMember>()
		val motherAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, mothers)
		binding.spinMom.adapter = motherAdapter
		
		// ? have spinners not able to select the same family member at the same time; causes render errors in BalkanJSTree
		binding.spinMom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
		{
			override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long)
			{
				val selected = motherAdapter.getItem(position)
				if(selected == fatherAdapter.getItem(binding.spinDad.selectedItemPosition))
					binding.spinMom.setSelection(0)
			}
			
			override fun onNothingSelected(parent: AdapterView<*>?)
			{
				return
			}
		}
		binding.spinDad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
		{
			override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long)
			{
				val selected = fatherAdapter.getItem(position)
				if(selected == motherAdapter.getItem(binding.spinMom.selectedItemPosition))
					binding.spinDad.setSelection(0)
			}
			
			override fun onNothingSelected(parentView: AdapterView<*>)
			{
				return
			}
		}
		
		// ? populate spinners with family members; by sex of males being fathers and females mothers and unknown in both spinners
		firebase.document(docPath).get().addOnSuccessListener { snapshot ->
			val tree = snapshot.toObject(FamilyTree::class.java)
			val members = tree?.members
			if(members != null)
			{
				motherAdapter.add(FamilyMember("Select", "Mother", id = FamilyMember.NULL_ID))
				motherAdapter.addAll(members.filter { it.sex != SexEnum.MALE })
				fatherAdapter.add(FamilyMember("Select", "Father", id = FamilyMember.NULL_ID))
				fatherAdapter.addAll(members.filter { it.sex != SexEnum.FEMALE })
				if(memberId != null)
				{
					// * remove themself from being their own parent
					motherAdapter.remove(tree.findMemberByID(memberId))
					fatherAdapter.remove(tree.findMemberByID(memberId))
				}
			}
		}
		
		// ? return adapters
		return Pair(fatherAdapter, motherAdapter)
	}
	
	private fun parseData(motherAdapter: ArrayAdapter<FamilyMember>, fatherAdapter: ArrayAdapter<FamilyMember>): FamilyMember
	{
		val firstName = binding.edtFName.text.toString()
		val lastName = binding.edtLName.text.toString()
		val sex =
				if(binding.sexSpinner.selectedItemPosition == Spinner.INVALID_POSITION)
					SexEnum.UNKNOWN
				else
					SexEnum.values()[binding.sexSpinner.selectedItemPosition]
		
		val birthdate =
				if(binding.edtBirthDate.text.toString().isNotEmpty())
					LocalDate.parse(binding.edtBirthDate.text.toString()).toEpochDay()
				else
					null
		val deathDate =
				if(binding.edtDeathDate.text.toString().isNotEmpty())
					LocalDate.parse(binding.edtDeathDate.text.toString()).toEpochDay()
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
		val member = FamilyMember(firstName, lastName, birthdate, deathDate, biography, sex = sex)
		
		member.mom = mom?.id
		member.dad = dad?.id
		return member
	}
	
	private fun uploadToDB(member: FamilyMember, docPath: String, memberId: String?)
	{
		firebase.document(docPath).get().addOnSuccessListener {
			val familyTree = it.toObject(FamilyTree::class.javaObjectType)!!
			
			// * Update last modified timestamp
			familyTree.lastModified = Timestamp.now()
			
			//? check if they are updating a predefined member in the tree
			if(memberId != null)
			{
				// * keep their memberId when updating/re-uploading them to DB
				member.id = memberId
				val oldVersion = familyTree.findMemberByID(memberId)
				familyTree.members.remove(oldVersion)
				familyTree.members.add(member)
				updateParentRelationships(familyTree, member)
				firebase.document(docPath).update("members", familyTree.members,
				                                  "lastModified", familyTree.lastModified)
					.addOnFailureListener { e ->
						Log.w("DB Failure", "Error updating document", e)
						Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
					}
				// * navigate back to member details
				finish()
			}
			else
			{
				//generate a new member Id for them
				member.id = collection.document().id
				familyTree.members.add(member)
				updateParentRelationships(familyTree, member)
				firebase.document(docPath)
					.update("members", familyTree.members,
					        "lastModified", familyTree.lastModified)
					.addOnFailureListener { e ->
						Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
						Log.e("DB Error", e.message ?: e.localizedMessage ?: e.toString())
					}
				//navigate back to family tree
				activityManager.startActivity(TreeActivity::class.java, docPath)
			}
		}
	}
	
	private fun updateParentRelationships(familyTree: FamilyTree, member: FamilyMember)
	{
		// ? update the parent if any to include the child in `kids`
		val mom = familyTree.findMemberByID(member.mom ?: FamilyMember.NULL_ID)
		mom?.addChild(member.id!!)
		val dad = familyTree.findMemberByID(member.dad ?: FamilyMember.NULL_ID)
		dad?.addChild(member.id!!)
		// ? add relationship between mom and dad
		mom?.addPartner(dad?.id ?: return)
		dad?.addPartner(mom?.id ?: return println("Mom: $mom+${mom?.id}\nDad: $dad+${dad.id}"))
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