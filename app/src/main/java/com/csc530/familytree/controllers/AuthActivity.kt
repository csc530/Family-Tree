package com.csc530.familytree.controllers

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.ActionCodeSettings

class AuthActivity : AppCompatActivity()
{
	
	// See: https://developer.android.com/training/basics/intents/result
	private val signInLauncher =
			registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
				this.onSignInResult(res)
			}
	
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		/* ? The prebuilt login flow */
		// Choose authentication providers
		val providers = arrayListOf(
				AuthUI.IdpConfig.EmailBuilder()
					.build(),
				//AuthUI.IdpConfig.PhoneBuilder().build(),
				AuthUI.IdpConfig.GoogleBuilder().build(),
		)
		
		// Create and launch sign-in intent
		val signInIntent: Intent = AuthUI.getInstance()
			.createSignInIntentBuilder()
			.setAvailableProviders(providers)
			.setLogo(R.drawable.family_tree)
			.build()
		signInLauncher.launch(signInIntent)
		
	}
	
	/**
	 * Handles if the user signed in successfully or failed to
	 * @param result Result the Sign in UI
	 */
	private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult)
	{
		val response = result.idpResponse
		if(result.resultCode == RESULT_OK)
		{
			// Successfully signed in
			finish()
			startActivity(Intent(this, LaunchActivity::class.java))
		}
		else // Sign in failed.
		{
			// * If response is null the user canceled the sign-in flow using the back button.
			if(response == null)
				startActivity(Intent(this, LaunchActivity::class.java))
			// * Otherwise check response.getError().getErrorCode() and handle the error.
			else
			{
				// * not null cuz as response is not null and it wasn't successfully thus must have error
				val msg = when(response.error!!.errorCode)
				          {
					          ErrorCodes.DEVELOPER_ERROR                   -> "Error, check the play store for updates"
					          ErrorCodes.EMAIL_LINK_PROMPT_FOR_EMAIL_ERROR -> "Please enter your email"
					          ErrorCodes.EMAIL_LINK_WRONG_DEVICE_ERROR     -> "Wrong device, please sign in with the correct device using the email link"
					          ErrorCodes.EMAIL_MISMATCH_ERROR              -> "Email mismatch, please verify your email"
					          ErrorCodes.ERROR_USER_DISABLED               -> "Your account has been disabled, please contact support"
					          ErrorCodes.INVALID_EMAIL_LINK_ERROR          -> "This sign in link is no longer valid"
					          ErrorCodes.NO_NETWORK                        -> "Poor network connection."
					          ErrorCodes.PLAY_SERVICES_UPDATE_CANCELLED    -> "Play services update required"
					          ErrorCodes.PROVIDER_ERROR                    -> "Login provider error."
					          ErrorCodes.UNKNOWN_ERROR                     -> "Error"
					          else                                         -> "ERROR"
				          } + " Please try again later."
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
				Log.e("AuthActivity", msg, response.error)
			}
		}
	}
	
}