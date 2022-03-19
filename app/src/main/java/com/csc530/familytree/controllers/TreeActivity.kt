package com.csc530.familytree.controllers


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityTreeBinding
import com.csc530.familytree.models.FamilyTree
import com.csc530.familytree.views.FamilyMemberView
import com.csc530.familytree.views.FamilyTreeGraphAdapter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.bandb.graphview.graph.Graph
import dev.bandb.graphview.graph.Node
import dev.bandb.graphview.layouts.tree.BuchheimWalkerConfiguration
import dev.bandb.graphview.layouts.tree.BuchheimWalkerLayoutManager
import dev.bandb.graphview.layouts.tree.TreeEdgeDecoration

class TreeActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityTreeBinding
	private lateinit var familyTree: FamilyTree
	private val treeViews = ArrayList<FamilyMemberView>()
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
		setupGraphView()
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
				firebase.document(docPath!!).get().addOnSuccessListener { document ->
					if(document == null)
						backToHome()
					familyTree = document.toObject(FamilyTree::class.java)!! //!!!!
					//? add view for each family member
					for((i, member) in familyTree.members.withIndex())
					{
						//TODO: draw lines for connecting members and add member to page consider webview
						// with https://balkan.app/FamilyTreeJS/Docs/GettingStarted
						val view = FamilyMemberView(this@TreeActivity)
						binding.rel.addView(view, i)
						view.firstName = member.firstName ?: "????"
						view.lastName = member.lastName ?: "?????"
						view.layoutParams.height = 250
						view.layoutParams.width = 250
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
	
	private fun setupGraphView()
	{
		val recycler = binding.recycler
		
		// 1. Set a layout manager of the ones described above that the RecyclerView will use.
		val configuration = BuchheimWalkerConfiguration.Builder()
			.setSiblingSeparation(100)
			.setLevelSeparation(100)
			.setSubtreeSeparation(100)
			.setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
			.build()
		recycler.layoutManager = BuchheimWalkerLayoutManager(this, configuration)
		
		// 2. Attach item decorations to draw edges
		recycler.addItemDecoration(TreeEdgeDecoration())
		
		// 3. Build your graph
		val graph = Graph()
		val node1 = Node("Parent")
		val node2 = Node("Child 1")
		val node3 = Node("Child 2")
		
		graph.addEdge(node1, node2)
		graph.addEdge(node1, node3)
		
		// 4. You will need a simple Adapter/ViewHolder.
		// 4.1 Your Adapter class should extend from `AbstractGraphAdapter`
		
		val adapter = FamilyTreeGraphAdapter()
		// 4.3 Submit the graph
		adapter.submitGraph(graph)
		recycler.adapter = adapter
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