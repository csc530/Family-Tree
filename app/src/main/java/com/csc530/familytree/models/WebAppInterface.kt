package com.csc530.familytree.models

import android.content.Intent
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.controllers.MemberDetailsActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/** Instantiate the interface and set the context  */
class WebAppInterface(private val activity: AppCompatActivity)
{
	private val activityManager = ActivityManager(activity)
	var nodes: ArrayList<Node?> = ArrayList()
	
	/**
	 * Return a JSON string of the nodes to be used within BalkanJSTree
	 * javascript file
	 * @return A JSON string of the nodes
	 */
	@JavascriptInterface
	fun getNodes(): String
	{
		val gson = Gson()
		val typeOf: Type = object : TypeToken<ArrayList<Node>>()
		{}.type
		return gson.toJson(nodes, typeOf)
	}
	
	/** Show a toast from the web page  */
	@JavascriptInterface
	fun showToast(toast: String)
	{
		Toast.makeText(activity, toast, Toast.LENGTH_LONG).show()
	}
	
	/**
	 * Navigates to the MemberDetailsActivity
	 * from the tree activity; to be called from JS file using BalkanJSTree
	 *
	 * @param memberId id of the member to display in the details activity
	 */
	@JavascriptInterface
	fun showDetails(memberId: String)
	{
		val docPath = activity.intent.getStringExtra("docPath")
		if(docPath == null)
			activityManager.backToHome()
		else
		{
			val intent = Intent(activity, MemberDetailsActivity::class.java)
			intent.putExtra("memberId", memberId)
			activityManager.startActivity(intent, docPath)
		}
	}
}