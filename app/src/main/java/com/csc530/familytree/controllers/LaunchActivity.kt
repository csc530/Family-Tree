package com.csc530.familytree.controllers

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityLaunchBinding
import com.csc530.familytree.models.ActivityManager
import com.csc530.familytree.models.FamilyTree
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class LaunchActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityLaunchBinding
	
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = ActivityLaunchBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		val user = Firebase.auth.currentUser
		// ? Remove the login and sign up text with a log out button if they are signed in
		if(user != null)
		{
			binding.txtLogin.visibility = TextView.GONE
			binding.txtOr.visibility = TextView.GONE
			binding.txtSignup.visibility = TextView.GONE
		}
		else //? remove logout button if they are not signed in
			binding.btnLogout.visibility = Button.GONE
		
		//Switch intents on button click
		binding.btnNewTree.setOnClickListener {
			if(user == null)
				Snackbar.make(this, binding.root, "Please log in or signup to continue", Snackbar.LENGTH_SHORT).show()
			else
			{
				// ? Create a dialog to enter a name for the new tree
				val getName = AlertDialog.Builder(this)
					.setTitle("Name family tree")
					.setMessage("What would you like to call this family tree?")
					.setNegativeButton("Cancel", null)
					.create()
				
				// * Create edit text to capture family tree name
				val editTxtFamTreeName = EditText(getName.context)
				editTxtFamTreeName.apply {
					hint = "The Johnson's"
					setSingleLine()
					inputType = InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
				}
				
				// * Add edit text to dialog
				getName.setView(editTxtFamTreeName)
				getName.setButton(AlertDialog.BUTTON_POSITIVE, "Create") { _, _ ->
					//	? ensure that a family tree name is entered
					if(editTxtFamTreeName.text.toString().isBlank())
					{
						// * Show error message
						AlertDialog.Builder(getName.context)
							.setIcon(android.R.drawable.stat_sys_warning)
							.setTitle("Name Required")
							.setMessage("Please enter a name for the family tree")
							.setNeutralButton("OK") { _, _ ->
								getName.show()
							}
							.show()
					}
					else
						createFamilyTree(editTxtFamTreeName.text.toString(), user.uid)
				}
				// * Show dialog
				getName.show()
			}
		}
		binding.btnLoadTree.setOnClickListener {
			ActivityManager.launchActivity(this, LoadTreeActivity::class.java)
		}
		//switch intents to authentication if they selected login or signup
		binding.vllAuth.setOnClickListener {
			ActivityManager.launchActivity(this, AuthActivity::class.java)
		}
		binding.btnLogout.setOnClickListener {
			// * Logout of firebase/account and reload home screen
			FirebaseAuth.getInstance().signOut()
			this.recreate()
		}
		
		binding.btnInfo.setOnClickListener {
			ActivityManager.launchActivity(this, InfoActivity::class.java)
		}
		
	}
	
	private fun createFamilyTree(name: String, uid: String)
	{
		val collection = FirebaseFirestore.getInstance().collection("Trees")
		// * Create a new family tree
		val familyTree = FamilyTree(name, uid, created = Timestamp.now(), lastModified = Timestamp.now())
		familyTree.id = collection.document().id
		// * Add family tree to firebase
		collection.document(familyTree.generateDocId())
			.set(familyTree)
			.addOnSuccessListener {
				ActivityManager.launchActivity(this, TreeActivity::class.java, familyTree.generateDocPath())
			}
			.addOnFailureListener {
				Snackbar.make(this, binding.root, "Failed to create family tree", Snackbar.LENGTH_SHORT).show()
			}
	}
	
	
}