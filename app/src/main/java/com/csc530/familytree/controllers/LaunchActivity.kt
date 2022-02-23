package com.csc530.familytree.controllers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityLaunchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LaunchActivity : AppCompatActivity() {
	private lateinit var auth: FirebaseAuth
	private lateinit var binding: ActivityLaunchBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityLaunchBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		auth = Firebase.auth
		// ? Remove the login and sign up text with a log out button if they are signed in
		if(auth.currentUser != null) {
			binding.txtLogin.visibility = TextView.GONE
			binding.txtOr.visibility = TextView.GONE
			binding.txtSignup.visibility = TextView.GONE
		}
		else //? remove logout button if they are not signed in
			binding.btnLogout.visibility = Button.GONE
		
		//Switch intents on button click
		binding.btnNewTree.setOnClickListener {
			val intent = Intent(this, TreeActivity::class.java)
			startActivity(intent)
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
		
	}
	
	public override fun onStart() {
		super.onStart()
		// Check if user is signed in (non-null) and update UI accordingly.
		val currentUser = auth.currentUser
		if(currentUser != null) {
			//TODO: add reload method that will get rid of the log in or sign up prompt/controls
			reload()
		}
	}
	
	/**
	 * Reloads the page to not display the sign in options but a logout button.
	 */
	fun reload() {}
}