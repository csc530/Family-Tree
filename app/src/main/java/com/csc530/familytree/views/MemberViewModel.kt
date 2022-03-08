package com.csc530.familytree.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csc530.familytree.models.Member
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MemberViewModel: ViewModel() {
	private val members = MutableLiveData<List<Member>>()
	private var auth : FirebaseAuth
	
	/**
	 * This is called after the constructor runs and can be used to setup our live data
	 */
	init{
		auth= Firebase.auth
		val userID = auth.currentUser?.uid
		
		//query the DB to get all the Projects for a specific user
		val db = FirebaseFirestore.getInstance().collection("tests")
			.whereEqualTo("uid", userID)
			.orderBy("name")
			.addSnapshotListener{ documents, exception ->
				if (exception != null)
				{
					Log.w("DB_Response", "Listen Failed ${exception.code}")
					return@addSnapshotListener
				}
				
				Log.i("DB_Response", "# of documents = ${documents!!.size()}")
				//loop over the documents and create Project objects
				documents?.let{
					val MemberList = ArrayList<Member>()
					for (document in documents)
					{
						Log.i("DB_Response", "${document.data}")
						
						//convert the JSON document into a Project object
						val project = document.toObject(Member::class.java)
						MemberList.add(project)
					}
					members.value = MemberList
				}
			}
	}
	
	fun getMembers() : LiveData<List<Member>>
	{
		return members
	}
}