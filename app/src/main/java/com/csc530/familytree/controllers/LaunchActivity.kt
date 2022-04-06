package com.csc530.familytree.controllers

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.csc530.familytree.databinding.ActivityLaunchBinding
import com.csc530.familytree.models.ActivityManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LaunchActivity : AppCompatActivity()
{
	private lateinit var auth: FirebaseAuth
	private lateinit var binding: ActivityLaunchBinding
	
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = ActivityLaunchBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		auth = Firebase.auth
		// ? Remove the login and sign up text with a log out button if they are signed in
		if(auth.currentUser != null)
		{
			binding.txtLogin.visibility = TextView.GONE
			binding.txtOr.visibility = TextView.GONE
			binding.txtSignup.visibility = TextView.GONE
		}
		else //? remove logout button if they are not signed in
			binding.btnLogout.visibility = Button.GONE
		
		//Switch intents on button click
		binding.btnNewTree.setOnClickListener {
			//!!! temporary until InfoActivity learn to make local saves of family trees
			if(auth.currentUser == null)
				Snackbar.make(this, binding.root, "Please log in or signup to continue", Snackbar.LENGTH_SHORT).show()
			else
			{
				val getName = AlertDialog.Builder(this)
					.setTitle("Name family tree")
					.setMessage("What would you like to call this family tree?")
					.create()
				//TODO refactor using with/or something
				val editTxtFamTreeName = EditText(getName.context)
				editTxtFamTreeName.hint = "The Johnson's"
				editTxtFamTreeName.setSingleLine()
				editTxtFamTreeName.inputType = InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
				editTxtFamTreeName.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
				getName.setView(editTxtFamTreeName)
				getName.setButton(AlertDialog.BUTTON_POSITIVE, "Create") { dialogInterface, button ->
//					? ensure that a family tree name is entered
					if(editTxtFamTreeName.text.toString().isEmpty())
					{
						AlertDialog.Builder(getName.context)
							//							.setIcon(R.drawable.warning/)
							.setTitle("Name Required")
							.setMessage("Please enter a name for the family tree")
							.setNeutralButton("OK"){dialogInterface,_->
								dialogInterface.dismiss()
								getName.show()
							}
							.show()
						return@setButton
					}
					dialogInterface.dismiss()
					val intent = Intent(this, TreeActivity::class.java)
					intent.putExtra("treeName", editTxtFamTreeName.text.toString())
					startActivity(intent)
				}
				getName.show()
			}
		}
		binding.btnLoadTree.setOnClickListener {
			val intent = Intent(this, LoadTreeActivity::class.java)
			startActivity(intent)
		}
		//switch intents to authentication if they selected login or signup
		binding.vllAuth.setOnClickListener {
			val intent = Intent(this, AuthActivity::class.java)
			startActivity(intent)
		}
		binding.btnLogout.setOnClickListener {
			FirebaseAuth.getInstance().signOut()
			this.recreate()
		}
		
		binding.btnInfo.setOnClickListener {
			ActivityManager.launchActivity(this, InfoActivity::class.java)
		}
		
	}
	
	
}