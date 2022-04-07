package com.csc530.familytree.controllers


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityTreeBinding
import com.csc530.familytree.models.ActivityManager
import com.csc530.familytree.models.FamilyTree
import com.csc530.familytree.models.WebAppInterface
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class TreeActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityTreeBinding
	private lateinit var familyTree: FamilyTree
	private lateinit var activityManager: ActivityManager
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = ActivityTreeBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		activityManager = ActivityManager(this)
		val auth = FirebaseAuth.getInstance()
		val firebase = FirebaseFirestore.getInstance()
		val collection = firebase.collection("Trees")
		var docPath = intent.getStringExtra("docPath")
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
		firebase.document(docPath).addSnapshotListener { snapshot, error ->
			if(error != null)
				Log.e("TreeActivity", "Error getting document: $error")
			else if(snapshot == null)
				Snackbar
					.make(binding.root, "Error please try again later, please hard refresh the page (click and hold refresh button)", Snackbar.LENGTH_LONG)
					.show()
			else
			{
				familyTree = snapshot.toObject(FamilyTree::class.java)!!
				println(familyTree)
				wai.nodes.clear()
				familyTree.populateRelationships()
				if(familyTree.members.size > 0)
				{
					//? add view for each family member
					for(member in familyTree.members)
						wai.nodes.add(member.toNode())
					wb.reload()
				}
				else
				// ? Hide webView so they can't add a node with balkan's native functionality; this causes errors as it's not registered to db
					wb.visibility = WebView.GONE
			}
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