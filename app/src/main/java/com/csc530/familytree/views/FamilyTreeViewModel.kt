package com.csc530.familytree.views

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csc530.familytree.models.FamilyTree
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FamilyTreeViewModel(val treePath: String? = null) : ViewModel()
{
	private val familyTrees = MutableLiveData<List<FamilyTree>>()
	private var auth: FirebaseAuth = Firebase.auth
	private val familyTree = MutableLiveData<FamilyTree>()
	
	/**
	 * This is called after the constructor runs and can be used to setup our live data
	 */
	init
	{
		val userID = auth.currentUser?.uid
		if(treePath != null)
		// * query firestore for specific family tree (and its changes)
			FirebaseFirestore.getInstance().collection("Trees")
				.document(treePath)
				.addSnapshotListener { value, error ->
					if(error != null)
					{
						Log.e("Firestore DB", error.localizedMessage ?: error.message ?: error.toString())
						return@addSnapshotListener
					}
					if(value != null)
						familyTree.value = value.toObject(FamilyTree::class.java)
				}
		else
		// * Query the DB to get all the family trees for a specific user
			FirebaseFirestore.getInstance().collection("Trees")
				.whereEqualTo("creator", userID)
				.addSnapshotListener { documents, error ->
					if(error != null)
					{
						Log.w("Firestore DB", error.localizedMessage ?: error.message ?: error.toString())
						return@addSnapshotListener
					}
					
					//loop over the documents and create Tree objects
					documents?.let {
						val tress = ArrayList<FamilyTree>()
						for(document in documents)
						{
							Log.i("DB_Response", "${document.data}")
							
							//convert the JSON document into a Project object
							val familyTree = document.toObject(FamilyTree::class.java)
							tress.add(familyTree)
						}
						familyTrees.value = tress
					}
				}
	}
	
	fun getFamilyTree(): MutableLiveData<FamilyTree>
	{
		return familyTree
	}
	
	fun getFamilyTrees(): MutableLiveData<List<FamilyTree>>
	{
		return familyTrees
	}
	
}