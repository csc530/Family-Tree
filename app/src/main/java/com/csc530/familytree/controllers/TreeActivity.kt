package com.csc530.familytree.controllers


import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.csc530.familytree.databinding.ActivityTreeBinding
import com.csc530.familytree.views.MemberAdapter
import com.csc530.familytree.views.MemberViewModel

class TreeActivity : AppCompatActivity() {
	private lateinit var binding: ActivityTreeBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityTreeBinding.inflate(layoutInflater)
		setContentView(binding.root)
		//connect RecyclerView with FirestoreDB via the ViewModel
		val viewModel: MemberViewModel by viewModels()
		viewModel.getProjects().observe(this) { members ->
			binding.recyclerView.adapter = MemberAdapter(this, members)
		}
	}
}