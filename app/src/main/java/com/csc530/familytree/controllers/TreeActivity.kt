package com.csc530.familytree.controllers


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityTreeBinding
import com.csc530.familytree.models.Member
import com.csc530.familytree.views.MemberView

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
		}
		//		//connect RecyclerView with FirestoreDB via the ViewModel
		//		val viewModel: MemberViewModel by viewModels()
		//		viewModel.getProjects().observe(this) {
		//			binding.recyclerView.adapter = MemberAdapter(this, it)
		//		}
		//		binding.button2.setOnClickListener {
		//			val db = FirebaseFirestore.getInstance().collection("tests")
		//			db.add(Member("pop", "dsa", null, null, null, null))
		//		}
	}
}