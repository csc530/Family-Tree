package com.csc530.familytree.views

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csc530.familytree.models.FamilyTree
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Family tree view model to handle the data flow between the view and the model of [FamilyTree]s
 *
 * @constructor Create [FamilyTreeViewModel] instance
 *
 * @param docPath Document path to a specific family tree to load
 */
class FamilyTreeViewModel(docPath: String? = null) : ViewModel()
{
	private val logTag = "Firestore DB"
	private val familyTrees = MutableLiveData<List<FamilyTree>?>()
	private val familyTree = MutableLiveData<FamilyTree?>()
	private val firestore = FirebaseFirestore.getInstance()
	
	
	/**
	 * This is called after the constructor runs and can be used to setup our live data
	 */
	init
	{
		val userID = FirebaseAuth.getInstance().currentUser?.uid
		if(docPath != null)
			getSingleTree(docPath)
		else
			getFamilyTrees(userID)
	}
	
	
	/**
	 * Query the DB to get all the family trees for a specific user
	 * if no documents are found or an error occurs, then the live data will be set to null
	 * @param userID The user's ID
	 */
	private fun getFamilyTrees(userID: String?)
	{
		// * query the DB for all the family trees for the user and add to live data
		// * via snapshot listener to the live data object
		firestore
			.collection("Trees")
			.whereEqualTo("creator", userID)
			.addSnapshotListener { documents, error ->
				if(documents != null)
					familyTrees.value = documents.toObjects(FamilyTree::class.java)
				else
				{
					if(error != null)
						Log.w(logTag, error.localizedMessage ?: error.message ?: error.toString())
					else
						Log.w(logTag, "No documents found")
					familyTrees.value = null
				}
			}
	}
	
	/**
	 * query firestore for specific family tree (and its changes)
	 * if there is an error or no document ([FamilyTree]) found, set the live data to null
	 * @receiver [FamilyTreeViewModel]
	 */
	private fun getSingleTree(docPath: String)
	{
		// * query the DB for the specific family tree and add to live data
		// * via snapshot listener to the live data object
		firestore.document(docPath)
			.addSnapshotListener { tree, error ->
				if(tree != null)
					familyTree.value = tree.toObject(FamilyTree::class.java)
				else
				{
					if(error != null)
						Log.e(logTag, error.localizedMessage ?: error.message ?: error.toString())
					else
						Log.e(logTag, "No documents found; No tree found")
					familyTree.value = null
				}
			}
	}
	
	/**
	 * Get family tree of given document path
	 *
	 * @return [MutableLiveData] of [FamilyTree] object or null if not found or an error occurred
	 */
	fun getFamilyTree(): MutableLiveData<FamilyTree?>
	{
		return familyTree
	}
	
	/**
	 * Get all family trees of given user
	 *
	 * @return [MutableLiveData] of [List] of [FamilyTree] objects or null if not found or an error occurred
	 */
	fun getFamilyTrees(): MutableLiveData<List<FamilyTree>?>
	{
		return familyTrees
	}
	
}