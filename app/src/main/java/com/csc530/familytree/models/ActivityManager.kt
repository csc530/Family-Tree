package com.csc530.familytree.models

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.controllers.LaunchActivity

/**
 * ActivityManager is a singleton class that manages the activities in the application.
 * @property activity the current activity to navigate from
 * @constructor Create [ActivityManager] object.
 * @author Christofer Cousins
 */
class ActivityManager(
		private val activity: AppCompatActivity,
)
{
	/**
	 * Navigate to the different activities in the application without instantiating an [ActivityManager] object.
	 */
	companion object
	{
		/**
		 * Navigate to the different activities in the application without instantiating an [ActivityManager] object.
		 * @param context the current activity
		 * @param activity the class of the activity to navigate to
		 */
		fun launchActivity(context: Context, activity: Class<out AppCompatActivity>)
		{
			val intent = Intent(context, activity)
			context.startActivity(intent)
		}
		
		/**
		 * Navigate to the different activities in the application without instantiating an [ActivityManager] object.
		 * @param context the current activity
		 * @param activity the class of the activity to navigate to
		 * @param docPath [FamilyTree] document path to pass to the activity as an extra
		 */
		fun launchActivity(context: Context, activity: Class<out AppCompatActivity>, docPath: String)
		{
			val intent = Intent(context, activity)
			intent.putExtra("docPath", docPath)
			context.startActivity(intent)
		}
		
	}
	
	/**
	 * Redirects back to homepage
	 * Back to home.
	 * Finishes the current activity and returns home
	 */
	fun backToHome()
	{
		activity.finish()
		activity.startActivity(Intent(activity, LaunchActivity::class.java))
	}
	
	/**
	 * Redirects back to homepage
	 * Back to home; finishes the current activity and returns home
	 * @param msg Message to display in a toast of the reason for redirecting back to home.
	 */
	fun backToHome(msg: String)
	{
		activity.finish()
		activity.startActivity(Intent(activity, LaunchActivity::class.java))
		Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
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
		activity.startActivity(intent)
	}
	
	/**
	 * Starts an activity to given intent
	 * The current intent is not finished (navigate back to on back button)
	 *
	 * @param intent Intent to navigate to
	 */
	fun startActivity(intent: Intent)
	{
		activity.startActivity(intent)
	}
	
	/**
	 * Starts an activity to given java class, the class is not checked to be valid
	 * The current intent is not finished (navigate back to on back button)
	 *
	 * @param activityClass Class to navigate to
	 */
	fun startActivity(activityClass: Class<*>)
	{
		activity.startActivity(Intent(activity, activityClass))
	}
	
	/**
	 * Starts an activity to given java class
	 * With a path to a firebase document; neither the class or document is validated
	 * The current intent is not finished (navigate back to on back button)
	 *
	 * @param clazz Class to navigate to
	 * @param docPath Path to document in firebase, not validated
	 
	 */
	fun startActivity(clazz: Class<*>, docPath: String)
	{
		val intent = Intent(activity, clazz)
		intent.putExtra("docPath", docPath)
		activity.startActivity(intent)
	}
	
	/**
	 * Starts an activity to given java class
	 * With a path to a firebase document and id of a family member; no parameters are validated
	 * The current intent is not finished (navigate back to on back button)
	 *
	 * @param clazz Class to navigate to
	 * @param docPath Path to document in firebase, not validated; key name= `docPath`
	 * @param memberId memberId to include; key name=`memberId`
	 */
	fun startActivity(clazz: Class<*>, docPath: String, memberId: String)
	{
		val intent = Intent(activity, clazz)
		intent.putExtra("docPath", docPath)
		intent.putExtra("memberId", memberId)
		activity.startActivity(intent)
	}
}