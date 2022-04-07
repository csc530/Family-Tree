package com.csc530.familytree.controllers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.csc530.familytree.R
import com.csc530.familytree.databinding.ActivityMemberDetailsBinding
import com.csc530.familytree.models.ActivityManager
import com.csc530.familytree.models.FamilyMember
import com.csc530.familytree.models.FamilyTree
import com.csc530.familytree.views.FamilyMemberAdapter
import com.csc530.familytree.views.FamilyTreeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


class MemberDetailsActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityMemberDetailsBinding
	private lateinit var activityManager: ActivityManager
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = ActivityMemberDetailsBinding.inflate(layoutInflater)
		setContentView(binding.root)
		activityManager = ActivityManager(this)
		
		// * validate the doc path; this is to parse a family member and prevent errors
		val docPath = intent.getStringExtra("docPath")
		val memberId = intent.getStringExtra("memberId")
		if(docPath == null || memberId == null)
			return activityManager.backToHome()
		
		binding.btnClose.setOnClickListener {
			finish()
		}
		binding.btnEdit.setOnClickListener {
			activityManager.startActivity(EditMemberActivity::class.java, docPath, memberId)
		}
	}
	
	override fun onStart()
	{
		//placed populate member so whenever they return from editing it will  with the current information
		// without needing to keep refreshing the page with new info in the DB
		super.onStart()
		// * validate the doc path; this is to parse a family member and prevent errors
		val docPath = intent.getStringExtra("docPath")
		val memberId = intent.getStringExtra("memberId")
		if(docPath == null || memberId == null)
			activityManager.backToHome()
		else
			populateMember(docPath, memberId)
		
	}
	
	val firebase = FirebaseFirestore.getInstance()
	
	private fun populateMember(docPath: String, memberID: String)
	{
		FamilyTreeViewModel(docPath).getMembers().observe(this) { members ->
			if(members == null) return@observe finish()
			val familyTree = FamilyTree(members = members)
			val member = familyTree.findMemberByID(memberID)
			if(member == null)
			{
				Toast.makeText(this, "Error, no such member", Toast.LENGTH_SHORT).show()
				finish()
				return@observe activityManager.startActivity(TreeActivity::class.java, docPath)
			}
			
			// ? bind member details to activity views
			binding.txtFullName.text = member.getFullName()
			binding.txtBirthday.text = member.getBirthday() ?: "-"
			binding.txtDeathday.text = member.getDeathday() ?: "-"
			//					binding.txtBiography.text = member.biography ?: "-"
			if(member.getAge() != -1)
				binding.txtAge.text = "${member.getAge()} years old"
			else
				binding.txtAge.text = "-"
			
			Picasso.get()
				.load(member.getImageUri())
				.placeholder(R.drawable.user)
				.into(binding.imgPortrait)
			
			//set up children and partner to display total number
			binding.txtChildren.text = "${member.children.size} kids"
			binding.txtPartners.text = "${member.partners.size} partners"
			
			binding.txtSex.text = member.sex.toString()
			
			//when the click the text display each child in a recycler view
			val kids = familyTree.getMembersByID(member.children)
			if(kids.isNotEmpty())
				binding.txtChildren.setOnClickListener {
					showMembers(kids, "${member.getFullName()}'s Children", docPath)
				}
			val partners = familyTree.getMembersByID(member.partners)
			if(partners.isNotEmpty())
				binding.txtPartners.setOnClickListener {
					showMembers(partners, "${member.getFullName()}'s Partners", docPath)
				}
		}
	}
	
	
	private fun showMembers(members: List<FamilyMember>, title: String, docPath: String)
	{
		val dialog = MaterialAlertDialogBuilder(this)
		dialog.setTitle(title)
		val recycler = RecyclerView(this)
		recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
		recycler.adapter = FamilyMemberAdapter(this, members, toMember(docPath))
		dialog.setView(recycler)
		dialog.setNeutralButton("Close") { dialogInterface, _ ->
			dialogInterface.dismiss()
		}.show()
	}
	
	private fun toMember(docPath: String): (FamilyMember, View) -> Unit = { familyMember: FamilyMember, _: View ->
		val intent = Intent(this, MemberDetailsActivity::class.java)
		intent.putExtra("memberId", familyMember.id)
		activityManager.startActivity(intent, docPath)
	}
}