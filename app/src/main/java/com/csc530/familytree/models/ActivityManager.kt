package com.csc530.familytree.models

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.controllers.LaunchActivity

class ActivityManager(
		val context: Context,
)
{
	
	/**
	 * Redirects back to homepage
	 * Back to home.
	 */
	fun backToHome(activity: AppCompatActivity)
	{
		activity.finish()
		context.startActivity(Intent(context, LaunchActivity::class.java))
	}
	
	/**
	 * Start activity with a path to a document in firebase to given intent.
	 * The current intent is not finished (navigate back to on back button)
	 *
	 * @param intent Intent to navigate to
	 * @param docPath Path to document in firebase, not validated
	 */
	fun startActivity(intent: Intent, docPath: String)
	{
		intent.putExtra("docPath", docPath)
		context.startActivity(intent)
	}
	
	/**
	 * Starts an activity to given intent
	 * The current intent is not finished (navigate back to on back button)
	 *
	 * @param intent Intent to navigate to
	 */
	fun startActivity(intent: Intent)
	{
		context.startActivity(intent)
	}
	
	/**
	 * Starts an activity to given java class, the class is not checked to be valid
	 * The current intent is not finished (navigate back to on back button)
	 *
	 * @param activityClass Class to navigate to
	 */
	fun startActivity(activityClass: Class<*>)
	{
		context.startActivity(Intent(context, activityClass))
	}
	/**
	 * Starts an activity to given java class
	 * With a path to a firebase document; neither the class or document is validated
	 * The current intent is not finished (navigate back to on back button)
	 *
	 * @param activityClass Class to navigate to
	 * @param docPath Path to document in firebase, not validated
	 
	 */
	fun startActivity(activityClass: Class<*>, docPath: String)
	{
		val intent = Intent(context, activityClass)
		intent.putExtra("docPath",docPath)
		context.startActivity(intent)
	}
}