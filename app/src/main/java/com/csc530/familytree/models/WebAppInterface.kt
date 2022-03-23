package com.csc530.familytree.models

import android.content.Context
import android.content.Intent
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.csc530.familytree.controllers.MemberDetailsActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/** Instantiate the interface and set the context  */
class WebAppInterface(private val mContext: Context)
{
	var nodes: ArrayList<Node?> = ArrayList<Node?>()
	
	@JavascriptInterface
	fun getNodes(): String
	{
		val gson = Gson()
		val typeOf: Type = object : TypeToken<ArrayList<Node>>()
		{}.type
		val json = gson.toJson(nodes, typeOf)
		println(json)
		return json
	}
	
	/** Show a toast from the web page  */
	@JavascriptInterface
	fun showToast(toast: String)
	{
		Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show()
	}
	
	@JavascriptInterface
	fun showDetails(memberID: String): Unit
	{
		val intent = Intent(mContext, MemberDetailsActivity::class.java)
		mContext.startActivity(intent)
	}
}