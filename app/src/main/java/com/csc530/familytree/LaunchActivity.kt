package com.csc530.familytree

import android.content.Intent
import android.os.Bundle
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
		
		// ? Remove the login and sign up text with a log out button if they are signed in
		auth = Firebase.auth
		if(auth.currentUser != null) {
			//TODO: remove login and sign up options
			//Todo add logout button
			binding.vllAuth.removeAllViews()
		}
		binding.vllAuth.removeAllViewsInLayout()
		
		binding = ActivityLaunchBinding.inflate(layoutInflater)
		setContentView(binding.root)
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