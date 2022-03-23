package com.csc530.familytree.controllers


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityTreeBinding
import com.csc530.familytree.models.FamilyTree
import com.csc530.familytree.models.WebAppInterface
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class TreeActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityTreeBinding
	private lateinit var familyTree: FamilyTree
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = ActivityTreeBinding.inflate(layoutInflater)
		setContentView(binding.root)
		val auth = FirebaseAuth.getInstance()
		val firebase = FirebaseFirestore.getInstance()
		val treeName = this.intent.getStringExtra("treeName")
		val collection = firebase.collection("Trees")
		var docPath = intent.getStringExtra("docPath")
		val wb = binding.webView
		
		wb.getSettings().setJavaScriptEnabled(true)
		val settings: WebSettings = wb.settings
		settings.javaScriptEnabled = true
		settings.domStorageEnabled = true
		
		//Listed as optimal settings for HTML5 (may need testing?).
		//Ref. http://stackoverflow.com/questions/10097233/optimal-webview-settings-for-  html5-support
		wb.isFocusable = true
		wb.isFocusableInTouchMode = true
		wb.settings.cacheMode = WebSettings.LOAD_NO_CACHE
		wb.settings.databaseEnabled = true
		wb.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
		binding.webView.settings.builtInZoomControls = true;
		wb.webViewClient = WebViewClient() // tells page not to open links in android browser and instead open them in this webview
		
		val wai = WebAppInterface(this)
		wai.activity = this
		wb.addJavascriptInterface(wai, "Android")
		binding.webView.loadUrl("file:///android_asset/familyTree.html")
		//create new family tree if no tree name is given
		if(auth.currentUser != null)
		{
			if(docPath == null && treeName != null)
			{
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
						backToHome()
					}
			}
			else if(docPath != null)
				firebase.document(docPath!!).get()
					.addOnSuccessListener { document ->
						if(document == null)
							backToHome()
						familyTree = document.toObject(FamilyTree::class.java)!! //!!!!
						//? add view for each family member
						for(member in familyTree.members)
							wai.nodes.add(member.toNode())
						wb.reload()
					}
					.addOnFailureListener {
						Log.e("Firebase", it.toString())
						backToHome()
					}
		}
		binding.fabAdd.setOnClickListener {
			val intent = Intent(this, EditMemberActivity::class.java)
			intent.putExtra("docPath", docPath)
			startActivity(intent)
		}
	}
	
	/**
	 * Redirects back to homepage
	 * Back to home.
	 */
	private fun backToHome()
	{
		finish()
		startActivity(Intent(this, LaunchActivity::class.java))
	}
}