package com.csc530.familytree.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csc530.familytree.models.FamilyMember
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FamilyMemberViewModel : ViewModel() {
	private val members = MutableLiveData<List<FamilyMember>>()
	private var auth: FirebaseAuth
	
	/**
	 * This is called after the constructor runs and can be used to setup our live data
	 */
	init {
		auth = Firebase.auth
		val userID = auth.currentUser?.uid
		
		//query the DB to get all the Projects for a specific user
		val db = FirebaseFirestore.getInstance().collection("Trees")
			.whereEqualTo("uid", userID)
			.addSnapshotListener { documents, exception ->
				if (exception != null) {
					Log.w("DB_Response", "Listen Failed ${exception.code}")
					return@addSnapshotListener
				}
				
				Log.i("DB_Response", "# of documents = ${documents!!.size()}")
				//loop over the documents and create Project objects
				documents.let {
					val membersList = ArrayList<FamilyMember>()
					for (document in documents) {
						Log.i("DB_Response", "${document.data}")
						
						//convert the JSON document into a Project object
						val project = document.toObject(FamilyMember::class.java)
						membersList.add(project)
					}
					members.value = membersList
				}
			}
	}
	
	fun getMembers(): LiveData<List<FamilyMember>> {
		return members
	}
}