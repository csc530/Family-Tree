package com.csc530.familytree.controllers

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Bitmap
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
		
		setupImageUpload()
		
		// ? setup spinners
		val (dadAdapter, momAdapter) = setupParentSpinners(docPath, memberId)
		
		// * populate form with current member details if they are updating a member
		if(memberId != null)
		{
			firebase.document(docPath).get()
				.addOnSuccessListener {
					val member = it.toObject(FamilyTree::class.java)?.findMemberByID(memberId)
					             ?: return@addOnSuccessListener activityManager.backToHome()
					binding.txtEdtTitle.text = resources.getText(R.string.edit_member)
					binding.edtFName.setText(member.firstName)
					binding.edtLName.setText(member.lastName)
					binding.sexSpinner.setSelection(member.sex.ordinal)
					binding.edtBirthDate.setText(member.getBirthDate().toString())
					binding.edtDeathDate.setText(member.getDeathDate().toString())
					Picasso.get()
						.load(member.getImageUri())
						.placeholder(R.drawable.user)
						.into(binding.btnImg)
				}
		}
		else
			binding.txtEdtTitle.text = resources.getText(R.string.add_member)
		
		binding.btnCncl.setOnClickListener { finish() }
		
		//? show datepicker when birth or date date is selected (gimmicky on click listener using focus change)
		binding.edtDeathDate.setOnFocusChangeListener { deathDate, hasFocus ->
			/*
			 * when the edit text is clicked take focus away and disable it
			 * to mitigate the chance to input invalid data
			 * and then show the date picker
			 * When the datepicker is closed, the focus is returned to the edit text
			 * and it can be "clicked" again
			 */
			deathDate.isEnabled = !hasFocus
			if(hasFocus)
				setDate(deathDate as EditText, "Death date")
		}
		binding.edtBirthDate.setOnFocusChangeListener { birthDate, hasFocus ->
			birthDate.isEnabled = !hasFocus
			if(hasFocus)
				setDate(birthDate as EditText, "Birth date")
		}
		
		binding.btnSubmit.setOnClickListener {
			// * check if the form is locked to prevent multiple submissions
			if(!binding.btnSubmit.isEnabled)
				return@setOnClickListener
			// * lock the form cancel and submit buttons; so that the user can't click them while the upload is in progress
			// (creating duplicates or cancelling the upload)
			binding.btnSubmit.isEnabled = false
			val member = parseData(momAdapter, dadAdapter)
			
			//? write to db if logged in
			if(auth.currentUser != null && member != null)
				uploadToDB(member, docPath, memberId)
			else
				activityManager.backToHome("Please login to add a member")
		}
	}
	
	/**
	 * Setup image upload.
	 * Initializes the result launcher for the file chooser Intent
	 * This needs to be done before onCreate or an error is thrown
	 */
	private fun setupImageUpload()
	{
		/**	 * Launches the file chooser Intent and handles the selected uri if any		 */
		val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
			binding.btnImg.setImageURI(uri)
			binding.btnImg.tag = uri
		}
		
		/**	 * Launches the file chooser Intent on the family member imageView		 */
		binding.btnImg.setOnClickListener {
			if(intent.resolveActivity(packageManager) != null)
				resultLauncher.launch("image/*")
		}
		
		// * reset uploaded image; when long clicked
		binding.btnImg.setOnLongClickListener {
			binding.btnImg.setImageResource(R.drawable.user)
			binding.btnImg.tag = null
			true
		}
	}
	
	/**
	 * Setup parent spinners.
	 * Adds all the family members in the family tree to each spinner and filters out any invalid parents
	 * from the mother and father adapter, that could cause errors
	 *
	 * @param docPath Document path to current family tree
	 * @param memberId Member id of currently edited member, if any
	 * @return a pair of adapters for the spinners, one for the mother and one for the father
	 */
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
		FamilyTreeViewModel(docPath).getFamilyTree().observe(this) { familyTree ->
			if(familyTree == null)
				return@observe finish()
			momAdapter.add(FamilyMember("Select", "Mother", id = FamilyMember.NULL_ID))
			momAdapter.addAll(familyTree.members.filter { it.sex != SexEnum.MALE })
			dadAdapter.add(FamilyMember("Select", "Father", id = FamilyMember.NULL_ID))
			dadAdapter.addAll(familyTree.members.filter { it.sex != SexEnum.FEMALE })
			
			val currentMember = familyTree.findMemberByID(memberId ?: FamilyMember.NULL_ID)
			
			if(memberId != null)
			{
				// * remove themself from being their own parent
				momAdapter.remove(currentMember)
				dadAdapter.remove(currentMember)
			}
			
			// ? set spinners to selected member's parents
			if(currentMember != null)
			{
				val dad = familyTree.findMemberByID(currentMember.father)
				val mom = familyTree.findMemberByID(currentMember.mother)
				binding.spinDad.setSelection(dadAdapter.getPosition(dad))
				binding.spinMom.setSelection(momAdapter.getPosition(mom))
			}
			
		}
		
		// ? return adapters
		return Pair(dadAdapter, momAdapter)
	}
	
	/**
	 * Parse data of family member from the activity form and return it as a FamilyMember object
	 *
	 * @param motherAdapter Mother adapter for the mom spinner
	 * @param fatherAdapter Father adapter for the dad spinner
	 * @return [FamilyMember] or null if any of the data is invalid
	 */
	private fun parseData(motherAdapter: ArrayAdapter<FamilyMember>, fatherAdapter: ArrayAdapter<FamilyMember>): FamilyMember?
	{
		//? get data from form
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
		
		// ? check if data is valid
		if(!validateData(firstName, lastName, birthdate, deathDate, mom, dad))
			return null
		
		// ? create family member object and return it
		val member = FamilyMember(firstName, lastName, birthdate, deathDate, sex = sex)
		member.mother = mom?.id
		member.father = dad?.id
		return member
		
	}
	
	/**
	 * Validate data against what least most amount of data we'll not cause errors when sent to BalkanJSTree
	 *
	 * @param firstName First name of the family member to be added
	 * @param lastName Last name of the family member to be added
	 * @param birthdate Birthdate of the family member to be added
	 * @param deathDate Death date of the family member to be added
	 * @param mom Mom id of the family member to be added
	 * @param dad Dad id of the family member to be added
	 * @return True if all the data is valid, false otherwise
	 */
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
			// * birthday must be before deathday
			birthday != null && deathday != null && birthday.isAfter(deathday) ->
			{
				Toast.makeText(this, "Birthdate cannot be after death date", Toast.LENGTH_SHORT).show()
				false
			}
			// * must have both parents or neither
			mom != null && dad == null || dad != null && mom == null           ->
			{
				Toast.makeText(this, "Both parents must be selected or none at all", Toast.LENGTH_SHORT).show()
				false
			}
			else                                                               -> true
		}
	}
	
	/**
	 * Add a new family member to the family tree
	 *
	 * @param member Family member to be added
	 */
	private fun uploadToDB(member: FamilyMember, docPath: String, memberId: String?)
	{
		// * check if they are uploading an image as well
		if(binding.btnImg.tag == null)
			uploadMember(member, docPath, memberId)
		else
			uploadImage(member, docPath, memberId)
	}
	
	/**
	 * Upload member to the database
	 *
	 * @param member Member to be added
	 * @param docPath Document path to the family Tree
	 * @param memberId Member id of the member to be updated, null if creating a new member
	 */
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
				// ? update the member in the tree; out with the old in with the new
				val oldVersion = familyTree.findMemberByID(memberId)
				familyTree.members.remove(oldVersion)
				familyTree.members.add(member)
				
				// ? delete old image in fireStorage if it exists and upload new one or deleting it
				if(oldVersion?.image != null && oldVersion.image != member.image)
					Firebase.storage.getReference(oldVersion.image!!).delete()
				
				// * update the member in the tree
				firebase.document(docPath).update("lastModified", familyTree.lastModified,
				                                  "members", familyTree.members)
					.addOnSuccessListener {
						// * navigate back to member details; only when db update is totally successful
						finish()
						Toast.makeText(this, "Member updated", Toast.LENGTH_SHORT).show()
					}
					.addOnFailureListener { e ->
						dbError(e)
					}
			}
			else
			{
				// ? generate a new member Id and assign it to the member
				member.id = collection.document().id
				
				// ? add the member to the tree
				familyTree.members.add(member)
				firebase.document(docPath).update("lastModified", familyTree.lastModified,
				                                  "members", familyTree.members)
					.addOnSuccessListener {
						// * navigate back to member details; only when db update is totally successful
						Toast.makeText(this, "Member added", Toast.LENGTH_SHORT).show()
						finish()
					}
					.addOnFailureListener { e ->
						dbError(e, "Failed to add member")
					}
			}
		}
			.addOnFailureListener { e ->
				dbError(e, "Failed to get family tree")
			}
	}
	
	/**
	 * Database error handler
	 * shows a toast with the error message to user and logs the error
	 *
	 * @param e Exception thrown
	 * @param msg Message to be shown to user
	 */
	private fun dbError(e: Exception, msg: String = "Please try again")
	{
		binding.btnSubmit.isEnabled = true
		Log.w("DB Failure", msg, e)
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
	}
	
	/**
	 * Upload image to fireStorage
	 *
	 * @param member Member to be added and uploaded
	 * @param docPath document path to the family tree
	 * @param memberId Member id of the member to be updated, null if creating a new member
	 */
	private fun uploadImage(member: FamilyMember, docPath: String, memberId: String?)
	{
		// * set the member id
		//!! critical for naming and retrieving the image from fireStorage
		if(memberId != null)
			member.id = memberId
		else
			member.id = collection.document().id
		
		// * upload image to firebase storage first to get it's uri
		val storage = Firebase.storage.reference
		val image = (binding.btnImg.drawable as BitmapDrawable).bitmap
		val imageRef: StorageReference
		val imageName = "Family-member-images/${member.id}"
		val baos = ByteArrayOutputStream()
		//  * To compress the image to a desired webp format requires the higher than desired SDK minimum for the app
		//  * so it's separated into a conditional
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
		
		// * upload the image to firebase storage
		imageRef.putBytes(baos.toByteArray())
			.addOnFailureListener {
				dbError(it, "Failed to upload image")
			}
			.addOnSuccessListener {
				// * get the image uri and set it to the member's image field
				imageRef.downloadUrl.addOnSuccessListener { uri ->
					member.image = uri.toString()
					//!! used member's id (member.id) to keep the member coherent with the firebase storage image name
					// ! and potentially prevent error retrieving image
					uploadMember(member, docPath, member.id)
				}
			}
	}
	
	/**
	 * onclick listener to display datepickers
	 *
	 * @param value The [EditText] to be edited with the datepicker's selected date
	 * @param title The title to display for datepicker dialog
	 */
	private fun setDate(value: EditText, title: String)
	{
		val date = DatePickerDialog(this)
		// * set the datepicker to the current date
		date.updateDate(LocalDate.now().year, LocalDate.now().monthValue, LocalDate.now().dayOfMonth)
		// * set the datepicker's title
		date.setTitle(title)
		// ? Make max date to today's date
		date.datePicker.maxDate = Date().toInstant().toEpochMilli()
		
		// ? When confirm is clicked update the given editText with the selected date String
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