package com.csc530.familytree

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityAuthBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.ui.auth.util.ExtraConstants
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {
	private lateinit var binding: ActivityAuthBinding
	private lateinit var auth: FirebaseAuth
	
	// See: https://developer.android.com/training/basics/intents/result
	private val signInLauncher =
			registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
				this.onSignInResult(res)
			}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		// Initialize Firebase Auth
		auth = Firebase.auth
		
		/* ? The prebuilt login flow */
		val actionCodeSettings = ActionCodeSettings.newBuilder()
			.setAndroidPackageName( /* yourPackageName= */
				"com.csc530.familytree",  /* installIfNotAvailable= */
				true,  /* minimumVersion= */
				null)
			.setHandleCodeInApp(true) // This must be set to true
			.setUrl("https://google.com") // This URL needs to be whitelisted
			.build()
		// Choose authentication providers
		val providers = arrayListOf(
			AuthUI.IdpConfig.EmailBuilder()
				.enableEmailLinkSignIn()
				.setActionCodeSettings(actionCodeSettings)
				.build(),
			//			AuthUI.IdpConfig.PhoneBuilder().build(),
			AuthUI.IdpConfig.GoogleBuilder().build(),
			//			AuthUI.IdpConfig.FacebookBuilder().build(),
			//			AuthUI.IdpConfig.TwitterBuilder().build()
		)
		var signInIntent: Intent? = null
		// Create and launch sign-in intent
		if (AuthUI.canHandleIntent(intent)) {
			println("Sign in")
			val extras = intent.extras ?: return
			val link = extras.getString(ExtraConstants.EMAIL_LINK_SIGN_IN)
			if (link != null) {
				signInIntent = AuthUI.getInstance()
					.createSignInIntentBuilder()
					.setEmailLink(link)
					.setAvailableProviders(providers)
					.build()
				signInLauncher.launch(signInIntent)
			}
		}
		else
			signInIntent = AuthUI.getInstance()
				.createSignInIntentBuilder()
				.setAvailableProviders(providers)
				.build()
		signInLauncher.launch(signInIntent)
		/*
			TODO: add email link for password-less sign in authentication
			https://firebase.google.com/docs/auth/android/email-link-auth?authuser=2#send_an_authentication_link_to_the_users_email_address
		*/
		
	}
	
	/**
	 * Handles if the user signed in successfully or failed to
	 * @param result Result the Sign in UI
	 */
	private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
		val response = result.idpResponse
		if (result.resultCode == RESULT_OK) {
			// Successfully signed in
			val user = FirebaseAuth.getInstance().currentUser
			// ...
		}
		else {
			// Sign in failed. If response is null the user canceled the
			// sign-in flow using the back button. Otherwise check
			// response.getError().getErrorCode() and handle the error.
			// ...
		}
	}
	
	public override fun onStart() {
		super.onStart()
		// Check if user is signed in (non-null) and update UI accordingly.
		val currentUser = auth.currentUser
		if (currentUser != null) {
			//TODO: add reload method that will get rid of the log in or sign up prompt/controls
			reload()
		}
	}
	
	/**
	 * Reloads the page to not display the sign in options but a logout button.
	 */
	fun reload() {}
}