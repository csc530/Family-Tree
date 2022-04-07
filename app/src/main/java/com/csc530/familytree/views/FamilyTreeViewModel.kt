package com.csc530.familytree.views

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csc530.familytree.models.FamilyMember
import com.csc530.familytree.models.FamilyTree
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class FamilyTreeViewModel(docPath: String? = null) : ViewModel()
{
	private val logTag = "Firestore DB"
	private val familyTrees = MutableLiveData<List<FamilyTree>?>()
	private var auth: FirebaseAuth = Firebase.auth
	private val familyTree = MutableLiveData<FamilyTree?>()
	private val members = MutableLiveData<ArrayList<FamilyMember>?>()
	private val firestore = FirebaseFirestore.getInstance()
	
	
	/**
	 * This is called after the constructor runs and can be used to setup our live data
	 */
	init
	{
		val userID = auth.currentUser?.uid
		if(docPath != null)
		{
			getSingleTree(docPath)
			getFamilyMembers(docPath)
		}
		else
			getFamilyTrees(userID)
	}
	
	/**
	 * Get the sub collection of members for the given tree by its document path
	 *
	 * @param docPath Document path to a family tree
	 */
	private fun getFamilyMembers(docPath: String)
	{
		firestore.collection("$docPath/members")
			.addSnapshotListener { snapshot, err ->
				if(err != null)
					Log.e(logTag, err.localizedMessage ?: err.message ?: err.toString())
				else if(snapshot != null)
				{
					val familyMembers = ArrayList<FamilyMember>()
					for(doc in snapshot.documents)
					{
						val familyMember = doc.toObject(FamilyMember::class.java)
						// *  add member to list if it's not null if it's is continue
						familyMembers.add(familyMember ?: continue)
					}
					// * add the family members to the live data
					members.value = familyMembers
					familyTree.value?.members = familyMembers
				}
			}
	}
	
	/**
	 * Query the DB to get all the family trees for a specific user
	 * @param userID The user's ID
	 */
	private fun getFamilyTrees(userID: String?)
	{
		firestore
			.collection("Trees")
			.whereEqualTo("creator", userID)
			.addSnapshotListener { documents, error ->
				if(error != null)
					Log.w(logTag, error.localizedMessage ?: error.message ?: error.toString())
				else if(documents != null)
				{
					val trees = ArrayList<FamilyTree>()
					for(document in documents)
					{
						//convert the JSON document into a Project object
						val familyTree = document.toObject(FamilyTree::class.java)
						trees.add(familyTree)
					}
					familyTrees.value = trees
				}
				else
				{
					Log.w(logTag, "No documents found")
					familyTrees.value = null
				}
			}
	}
	
	/**
	 * query firestore for specific family tree (and its changes)
	 *
	 * @receiver [FamilyTreeViewModel]
	 */
	private fun getSingleTree(docPath: String)
	{
		firestore.document(docPath).addSnapshotListener { value, error ->
			if(error != null)
				Log.e(logTag, error.localizedMessage ?: error.message ?: error.toString())
			else if(value != null)
			{
				// * add the family tree to the live data
				familyTree.value = value.toObject(FamilyTree::class.java)
			}
			else
			{
				Log.e(logTag, "No value returned")
				familyTree.value = null
			}
		}
	}
	
	fun getFamilyTree(): MutableLiveData<FamilyTree?>
	{
		return familyTree
	}
	
	fun getFamilyTrees(): MutableLiveData<List<FamilyTree>?>
	{
		return familyTrees
	}
	
	fun getMembers(): MutableLiveData<ArrayList<FamilyMember>?>
	{
		return members
	}
	
}