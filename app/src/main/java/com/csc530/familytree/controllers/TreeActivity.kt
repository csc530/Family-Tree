package com.csc530.familytree.controllers


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityTreeBinding
import com.csc530.familytree.models.Tree
import com.csc530.familytree.views.MemberView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TreeActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityTreeBinding
	private lateinit var familyTree: Tree
	private val treeViews = ArrayList<MemberView>()
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
		//create new family tree if no tree name is given
		if(auth.currentUser != null)
		{
			if(docPath == null && treeName != null)
			{
				familyTree = Tree(treeName, auth.currentUser!!.uid, null, created = Timestamp.now(), lastModified = Timestamp.now())
				val docID = "$treeName-${auth.currentUser!!.displayName}-${collection.document().id}"
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
				firebase.document(docPath!!).get().addOnSuccessListener { document ->
					if(document == null)
						backToHome()
					familyTree = document.toObject(Tree::class.java)!! //!!!!
					//? add view for each family member
					for(member in familyTree.members)
					{
						val view = MemberView(this@TreeActivity)
						view.firstName = member.firstName ?: "????"
						view.lastName = member.lastName ?: "?????"
						view.layoutParams.height = 150
						view.layoutParams.width = 150
						binding.root.addView(view)
					}
				}.addOnFailureListener {
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