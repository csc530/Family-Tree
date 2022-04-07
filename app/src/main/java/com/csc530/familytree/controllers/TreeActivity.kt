package com.csc530.familytree.controllers


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityTreeBinding
import com.csc530.familytree.models.ActivityManager
import com.csc530.familytree.models.FamilyTree
import com.csc530.familytree.models.WebAppInterface
import com.csc530.familytree.views.FamilyTreeViewModel


class TreeActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityTreeBinding
	
	private lateinit var activityManager: ActivityManager
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = ActivityTreeBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		activityManager = ActivityManager(this)
		val docPath = intent.getStringExtra("docPath")
		val wb = binding.webView
		
		val settings: WebSettings = wb.settings
		settings.javaScriptEnabled = true
		settings.domStorageEnabled = true
		
		//Listed as optimal settings for HTML5 (may need testing?).
		//Ref. http://stackoverflow.com/questions/10097233/optimal-webview-settings-for-  html5-support
		wb.isFocusable = true
		wb.isFocusableInTouchMode = true
		settings.cacheMode = WebSettings.LOAD_NO_CACHE
		settings.databaseEnabled = true
		wb.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
		binding.webView.settings.builtInZoomControls = true;
		wb.webViewClient = WebViewClient() // tells page not to open links in android browser and instead open them in this webview
		
		val wai = WebAppInterface(this)
		wb.addJavascriptInterface(wai, "Android")
		binding.webView.loadUrl("file:///android_asset/familyTree.html")
		//create new family tree if no tree name is given
		// * logic for loading a pre-existing family-tree
		if(docPath == null)
			return activityManager.backToHome()
		
		FamilyTreeViewModel(docPath).getMembers().observe(this) { members ->
			if(members == null) return@observe activityManager.backToHome("No members found")
			val familyTree = FamilyTree(members = members)
			wai.nodes.clear()
			familyTree.populateRelationships()
			//? add view for each family member
			for(member in familyTree.members)
				wai.nodes.add(member.toNode())
			if(familyTree.members.isNullOrEmpty()) // ? Hide webView so they can't add a node with balkan's native functionality; this causes errors as it's not registered to db
				wb.visibility = WebView.GONE
			wb.reload()
		}
		
		binding.imgbtnHome.setOnClickListener {
			activityManager.startActivity(LaunchActivity::class.java)
		}
		binding.imgbtnRefresh.setOnClickListener { wb.reload() }
		// * "hard" refresh the view to reload the tree from db not just redraw the current tree
		binding.imgbtnRefresh.setOnLongClickListener { recreate(); true }
		binding.fabAdd.setOnClickListener {
			val intent = Intent(this, EditMemberActivity::class.java)
			intent.putExtra("docPath", docPath)
			startActivity(intent)
		}
	}
}