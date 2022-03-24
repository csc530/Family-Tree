package com.csc530.familytree.controllers


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityTreeBinding
import com.csc530.familytree.models.ActivityManager
import com.csc530.familytree.models.FamilyTree
import com.csc530.familytree.models.WebAppInterface
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class TreeActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityTreeBinding
	private lateinit var familyTree: FamilyTree
	private lateinit var activityManager:ActivityManager
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = ActivityTreeBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		activityManager = ActivityManager(this)
		val auth = FirebaseAuth.getInstance()
		val firebase = FirebaseFirestore.getInstance()
		val treeName = this.intent.getStringExtra("treeName")
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
		if(auth.currentUser != null)
		{
			// * logic if they just created a new Family tree
			if(docPath == null && treeName != null)
			{
				// ? Hide webView so they can't add a node with balkan's native functionality; this causes errors as it's not registered to db
				wb.visibility = WebView.GONE
				
				familyTree = FamilyTree(treeName, auth.currentUser!!.uid, created = Timestamp.now(), lastModified = Timestamp.now())
				familyTree.id = collection.document().id
				val docID = "$treeName-${auth.currentUser!!.uid}-${familyTree.id}"
				collection.document(docID)
					.set(familyTree)
					.addOnSuccessListener {
						docPath = collection.document(docID).path
						intent.putExtra("docPath", docPath)
					}
					.addOnFailureListener {
						activityManager.backToHome(this)
					}
			}
			// * logic for loading a pre-existing family-tree
			else if(docPath != null)
				firebase.document(docPath!!).get()
					.addOnSuccessListener { document ->
						if(document == null)
							activityManager.backToHome(this)
						familyTree = document.toObject(FamilyTree::class.java)!!
						//? add view for each family member
						for(member in familyTree.members)
							wai.nodes.add(member.toNode())
						wb.reload()
					}
					.addOnFailureListener {
						Log.e("Firebase", it.toString())
						activityManager.backToHome(this)
					}
		}
		binding.fabAdd.setOnClickListener {
			val intent = Intent(this, EditMemberActivity::class.java)
			intent.putExtra("docPath", docPath)
			startActivity(intent)
		}
	}
	
}