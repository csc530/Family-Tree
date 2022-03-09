package com.csc530.familytree.controllers


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityTreeBinding
import com.csc530.familytree.models.Member
import com.csc530.familytree.views.MemberView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TreeActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityTreeBinding
	private val family = ArrayList<Member>()
	private val familyTree = ArrayList<MemberView>()
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = ActivityTreeBinding.inflate(layoutInflater)
		setContentView(binding.root)
		binding.imgbtnAdd.setOnClickListener {
			startActivity(Intent(this, EditMemberActivity::class.java))
			val auth = FirebaseAuth.getInstance().currentUser
			if(auth != null)
			{
				//TODO: load family members from firebase
			}
		}
	}
}