package com.csc530.familytree.controllers


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityTreeBinding
import com.csc530.familytree.models.Member
import com.csc530.familytree.views.MemberView
import com.csc530.familytree.views.MemberViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TreeActivity : AppCompatActivity() {
	private lateinit var binding: ActivityTreeBinding
	private val family = ArrayList<Member>()
	private val familyTree = ArrayList<MemberView>()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityTreeBinding.inflate(layoutInflater)
		setContentView(binding.root)
		binding.imgbtnAdd.setOnClickListener {
			startActivity(Intent(this, EditMemberActivity::class.java))
			val auth = FirebaseAuth.getInstance().currentUser
			if (auth != null) {
				val tree = MemberViewModel().getMembers()
				tree.observe(this) {
					for (member in it) {
						val view = MemberView(this)
						view.firstName = member.firstName ?: ""
						view.lastName = member.lastName ?: ""
						binding.root.addView(view)
					}
				}
			}
		}
	}
}