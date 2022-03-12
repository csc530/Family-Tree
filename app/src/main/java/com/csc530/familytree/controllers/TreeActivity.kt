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
		val firebase = FirebaseFirestore.getInstance().collection("Trees")
		val treeName = this.intent.getStringExtra("treeName")!!
		
		//create new family tree if no tree name is given
		if(auth.currentUser != null)
		{
			firebase.whereEqualTo("creator", auth.currentUser!!.uid).whereEqualTo("name", treeName)
				.limit(1)
				.get().addOnSuccessListener { querySnap ->
					if(querySnap.documents.isNotEmpty())
						for(document in querySnap)
						{
							familyTree = document.toObject(Tree::class.java) //!!!!
							//? add view for each family member
							for(member in familyTree.members)
							{
								val view = MemberView(this@TreeActivity)
								view.firstName = member.firstName ?: "????"
								view.lastName = member.lastName ?: "?????"
								//								view.layoutParams.height = 150
								//								view.layoutParams.width = 150
							}
						}
					else
					{
						familyTree = Tree(treeName, auth.currentUser!!.uid, null, created = Timestamp.now(), lastModified = Timestamp.now())
						firebase.document("$treeName-${auth.currentUser!!.displayName}-${firebase.document().id}")
							.set(familyTree)
							.addOnFailureListener {
								//redirect back to homepage
								startActivity(Intent(this, LaunchActivity::class.java))
							}
					}
				}.addOnFailureListener { Log.e("Firebase", it.toString()) }
		}
		binding.imgbtnAdd.setOnClickListener {
			val intent = Intent(this, EditMemberActivity::class.java)
			var path: String
			firebase.whereEqualTo("creator", auth.currentUser?.uid)
				.whereEqualTo("name", treeName).get().addOnSuccessListener {
					path = it.documents[0].reference.path
					intent.putExtra("docPath", path)
					startActivity(intent)
				}
		}
	}
}