package com.csc530.familytree.controllers

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.R
import com.csc530.familytree.databinding.ActivityEditMemberBinding
import com.csc530.familytree.models.ActivityManager
import com.csc530.familytree.models.FamilyMember
import com.csc530.familytree.models.FamilyTree
import com.csc530.familytree.models.SexEnum
import com.csc530.familytree.views.FamilyTreeViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
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
		
		val docPath = intent.getStringExtra("docPath") ?: return activityManager.backToHome()
		val memberId = intent.getStringExtra("memberId")
		
		// ? setup spinners
		val (dadAdapter, momAdapter) = setupParentSpinners(docPath, memberId)
		// * populate form with current member details if they are updating a member
		if(memberId != null)
		{
			FamilyTreeViewModel(docPath).getMembers().observe(this) { members ->
				if(members == null)
					return@observe finish()
				val member = members.find { it.id == memberId } ?: return@observe finish()
				binding.txtEdtTitle.text = resources.getText(R.string.edit_member)
				binding.edtFName.setText(member.firstName)
				binding.edtLName.setText(member.lastName)
				binding.sexSpinner.setSelection(member.sex.ordinal)
				if(member.getBirthDate() != null)
					binding.edtBirthDate.setText(member.getBirthDate().toString())
				if(member.getDeathDate() != null)
					binding.edtDeathDate.setText(member.getDeathDate().toString())
				Picasso.get()
					.load(member.getImageUri())
					.placeholder(R.drawable.user)
					.into(binding.btnImg)
			}
		}
		else
			binding.txtEdtTitle.text = resources.getText(R.string.add_member)
		
		setupImageUpload()
		
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
			val member = parseData(momAdapter, dadAdapter)
			
			//? write to db if logged in
			if(auth.currentUser != null && member != null)
				uploadToDB(member, docPath, memberId)
			else
				activityManager.backToHome("Please login to add a member")
		}
	}
	
	private fun setupImageUpload()
	{
		val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
			if(it == null)
				return@registerForActivityResult
			val bitmap = BitmapFactory.decodeFile(it.toString())
			binding.btnImg.setImageBitmap(bitmap)
		}
		val selectLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
			binding.btnImg.setImageURI(uri)
			binding.btnImg.tag = uri
		}
		
		binding.btnImg.setOnClickListener {
			if(intent.resolveActivity(packageManager) != null)
				selectLauncher.launch("image/*")
		}
		// * reset uploaded image; when long clicked
		binding.btnImg.setOnLongClickListener {
			binding.btnImg.setImageResource(R.drawable.user)
			binding.btnImg.tag = null
			true
		}
	}
	
	
	private fun setupParentSpinners(docPath: String, memberId: String?): Pair<ArrayAdapter<FamilyMember>, ArrayAdapter<FamilyMember>>
	{
		//? setup parent spinners
		val fathers: ArrayList<FamilyMember> = ArrayList<FamilyMember>()
		val dadAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, fathers)
		binding.spinDad.adapter = dadAdapter
		val mothers = ArrayList<FamilyMember>()
		val momAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, mothers)
		binding.spinMom.adapter = momAdapter
		
		// ? have spinners not able to select the same family member at the same time; causes render errors in BalkanJSTree
		binding.spinMom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
		{
			override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long)
			{
				val selected = momAdapter.getItem(position)
				if(selected == dadAdapter.getItem(binding.spinDad.selectedItemPosition))
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
				val selected = dadAdapter.getItem(position)
				if(selected == momAdapter.getItem(binding.spinMom.selectedItemPosition))
					binding.spinDad.setSelection(0)
			}
			
			override fun onNothingSelected(parentView: AdapterView<*>)
			{
				return
			}
		}
		
		// ? populate spinners with family members; by sex of males being fathers and females mothers and unknown in both spinners
		FamilyTreeViewModel(docPath).getMembers().observe(this) { members ->
			if(members == null)
				return@observe finish()
			momAdapter.add(FamilyMember("Select", "Mother", id = FamilyMember.NULL_ID))
			momAdapter.addAll(members.filter { it.sex != SexEnum.MALE })
			dadAdapter.add(FamilyMember("Select", "Father", id = FamilyMember.NULL_ID))
			dadAdapter.addAll(members.filter { it.sex != SexEnum.FEMALE })
			if(memberId != null)
			{
				// * remove themself from being their own parent
				momAdapter.remove(members.find { it.id == memberId })
				dadAdapter.remove(members.find { it.id == memberId })
			}
			
			// ? set spinners to selected member's parents
			val member = members.find { it.id == memberId }
			if(member != null)
			{
				val dad = members.find { it.id == member.father }
				val mom = members.find { it.id == member.mother }
				binding.spinDad.setSelection(dadAdapter.getPosition(dad))
				binding.spinMom.setSelection(momAdapter.getPosition(mom))
			}
			
		}
		
		// ? return adapters
		return Pair(dadAdapter, momAdapter)
	}
	
	private fun parseData(motherAdapter: ArrayAdapter<FamilyMember>, fatherAdapter: ArrayAdapter<FamilyMember>): FamilyMember?
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
		
		
		//		val biography = binding.taOther.text.toString()
		if(!validateData(firstName, lastName, birthdate, deathDate, mom, dad))
			return null
		
		val member = FamilyMember(firstName, lastName, birthdate, deathDate, sex = sex)
		
		member.mother = mom?.id
		member.father = dad?.id
		return member
		
	}
	
	private fun validateData(firstName: String, lastName: String, birthdate: Long?, deathDate: Long?, mom: FamilyMember?, dad: FamilyMember?): Boolean
	{
		val birthday: LocalDate? = if(birthdate != null)
			LocalDate.ofEpochDay(birthdate)
		else
			null
		val deathday: LocalDate? = if(deathDate != null)
			LocalDate.ofEpochDay(deathDate)
		else
			null
		return when
		{
			birthday != null && deathday != null && birthday.isAfter(deathday) ->
			{
				Toast.makeText(this, "Birthdate cannot be after death date", Toast.LENGTH_SHORT).show()
				false
			}
			mom != null && dad == null || dad != null && mom == null           ->
			{
				Toast.makeText(this, "Both parents must be selected or none at all", Toast.LENGTH_SHORT).show()
				false
			}
			else                                                               -> true
		}
	}
	
	private fun uploadToDB(member: FamilyMember, docPath: String, memberId: String?)
	{
		if(binding.btnImg.tag == null)
			uploadMember(member, docPath, memberId)
		else
			uploadImage(member, docPath, memberId)
	}
	
	private fun uploadMember(member: FamilyMember, docPath: String, memberId: String?)
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
				// ? delete old image in fireStorage if it exists
				if(oldVersion?.image != null)
					Firebase.storage.getReference(oldVersion.image!!).delete()
				// * delete old member document; by (unique) id (in case the name and hence forth the doc id changed
				firebase.collection(familyTree.generateDocPath() + "/members")
					.whereEqualTo("id", memberId)
					.get()
					.addOnSuccessListener { oldMemberDocs ->
						for(doc in oldMemberDocs)
							doc.reference.delete()
						// * can't be null as this member is added above
						val memberPath = familyTree.generateDocPath(member)!!
						firebase.document(memberPath).set(member)
							.addOnSuccessListener {
								firebase.document(docPath).update("lastModified", familyTree.lastModified)
									.addOnSuccessListener {
										// * navigate back to member details; only when db update is totally successful
										finish()
									}
									.addOnFailureListener { e ->
										Log.w("DB Failure", "Error updating document", e)
										Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
									}
								Toast.makeText(this, "Member updated", Toast.LENGTH_SHORT).show()
								finish()
							}
							.addOnFailureListener {
								Toast.makeText(this, "Failed to update member", Toast.LENGTH_SHORT).show()
							}
					}
			}
			else
			{
				//generate a new member Id for them
				member.id = collection.document().id
				familyTree.members.add(member)
				// * can't be null as this member is added above
				val memberPath = familyTree.generateDocPath(member)!!
				firebase.document(memberPath).set(member)
					.addOnSuccessListener {
						firebase.document(docPath).update("lastModified", familyTree.lastModified)
							.addOnSuccessListener {
								// * navigate back to member details; only when db update is totally successful
								finish()
							}
							.addOnFailureListener { e ->
								Log.w("DB Failure", "Error updating document", e)
								Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
							}
						Toast.makeText(this, "Member added", Toast.LENGTH_SHORT).show()
						finish()
					}
					.addOnFailureListener {
						Toast.makeText(this, "Failed to add member", Toast.LENGTH_SHORT).show()
					}
			}
		}
			.addOnFailureListener {
				Toast.makeText(this, "Could not find family tree, please try again later", Toast.LENGTH_SHORT).show()
				Log.e("DB Error", it.message ?: it.localizedMessage ?: it.toString())
				activityManager.backToHome()
			}
	}
	
	private fun uploadImage(member: FamilyMember, docPath: String, memberId: String?)
	{
		// * upload image to firebase storage first to get it's uri
		val storage = Firebase.storage.reference
		val image = (binding.btnImg.drawable as BitmapDrawable).bitmap
		val imageRef: StorageReference
		val imageName = "Family-member-images/${member.getFullName()}-${member.id}"
		val baos = ByteArrayOutputStream()
		// To compress the image to a desired webp format requires the higher than desired SDK minimum for the app
		// so it's separated into a conditional
		imageRef = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
		{
			image.compress(Bitmap.CompressFormat.WEBP_LOSSY, 100, baos)
			storage.child("$imageName.webp")
		}
		else
		{
			image.compress(Bitmap.CompressFormat.PNG, 100, baos)
			storage.child("$imageName.png")
		}
		
		val uploadTask = imageRef.putBytes(baos.toByteArray())
		uploadTask
			.addOnFailureListener {
				Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
			}
			.addOnSuccessListener {
				imageRef.downloadUrl.addOnSuccessListener { uri ->
					member.image = uri.toString()
					uploadMember(member, docPath, memberId)
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