package com.csc530.familytree

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityAuthBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.ui.auth.util.ExtraConstants
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
	
	// See: https://developer.android.com/training/basics/intents/result
	private val signInLauncher =
			registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
				this.onSignInResult(res)
			}
	private lateinit var binding: ActivityAuthBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		/*//? On back button go to the start page and not an empty middle man (page/activity)
		binding = ActivityAuthBinding.inflate(layoutInflater)
		this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
			override fun handleOnBackPressed() {
				val intent = Intent(this@AuthActivity, LaunchActivity::class.java)
				this.remove()
				startActivity(intent)
			}
		})*/
		
		/* ? The prebuilt login flow */
		val actionCodeSettings = ActionCodeSettings.newBuilder()
			.setAndroidPackageName(
				/* yourPackageName= */
				"com.csc530.familytree",
				/* installIfNotAvailable= */
				true,
				/* minimumVersion= */
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
		
		lateinit var signInIntent: Intent
		// Create and launch sign-in intent
		if(AuthUI.canHandleIntent(intent)) {
			val extras = intent.extras ?: return
			val link = extras.getString(ExtraConstants.EMAIL_LINK_SIGN_IN)
			if(link != null) {
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
		if(result.resultCode == RESULT_OK) {
			// Successfully signed in
			val user = FirebaseAuth.getInstance().currentUser
			// ...
		}
		else {
			// Sign in failed.
			// If response is null the user canceled the sign-in flow using the back button.
			if(response == null)
				startActivity(Intent(this, LaunchActivity::class.java))
			// Otherwise check response.getError().getErrorCode() and handle the error.
			else {
				// * not null cuz as response not null and it wasn't successfully thus must have error
				val msg = when(response.error!!.errorCode) {
					          ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT          -> {
						          TODO()
					          }
					          ErrorCodes.DEVELOPER_ERROR                           -> {
						          TODO()
					          }
					          ErrorCodes.EMAIL_LINK_CROSS_DEVICE_LINKING_ERROR     -> {
						          TODO()
					          }
					          ErrorCodes.EMAIL_LINK_DIFFERENT_ANONYMOUS_USER_ERROR -> {
						          TODO()
					          }
					          ErrorCodes.EMAIL_LINK_PROMPT_FOR_EMAIL_ERROR         -> {
						          TODO()
					          }
					          ErrorCodes.EMAIL_LINK_WRONG_DEVICE_ERROR             -> {
						          TODO()
					          }
					          ErrorCodes.EMAIL_MISMATCH_ERROR                      -> {
						          TODO()
					          }
					          ErrorCodes.ERROR_GENERIC_IDP_RECOVERABLE_ERROR       -> {
						          TODO()
					          }
					          ErrorCodes.ERROR_USER_DISABLED                       -> {
						          TODO()
					          }
					          ErrorCodes.INVALID_EMAIL_LINK_ERROR                  -> "This sign in link is no longer valid"
					          ErrorCodes.NO_NETWORK                                -> {
						          "Poor network connection."
					          }
					          ErrorCodes.PLAY_SERVICES_UPDATE_CANCELLED            -> "Play services update required"
					          ErrorCodes.PROVIDER_ERROR                            -> {
						          TODO()
					          }
					          ErrorCodes.UNKNOWN_ERROR                             -> "Error"
					          else                                                 -> "ERROR."
				          } + " Please try again later."
				Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
			}
		}
	}
	
}