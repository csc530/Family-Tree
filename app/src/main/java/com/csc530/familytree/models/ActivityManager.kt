package com.csc530.familytree.models

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.controllers.LaunchActivity

class ActivityManager(
		private val activity: AppCompatActivity,
)
{
	companion object
	{
		fun launchActivity(context: Context, activity: Class<out AppCompatActivity>)
		{
			val intent = Intent(context, activity)
			context.startActivity(intent)
		}
		fun launchActivity(context: Context, activity: Class<out AppCompatActivity>, docPath: String)
		{
			val intent = Intent(context, activity)
			intent.putExtra("docPath", docPath)
			context.startActivity(intent)
		}
		
		fun toHome(activity: AppCompatActivity)
		{
			activity.finish()
			activity.startActivity(Intent(activity, LaunchActivity::class.java))
		}
	}
	
	/**
	 * Redirects back to homepage
	 * Back to home.
	 */
	fun backToHome()
	{
		activity.finish()
		activity.startActivity(Intent(activity, LaunchActivity::class.java))
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