package com.csc530.familytree.controllers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.csc530.familytree.R
import com.csc530.familytree.databinding.ActivityMemberDetailsBinding
import com.csc530.familytree.models.ActivityManager
import com.csc530.familytree.models.FamilyTree
import com.google.firebase.firestore.FirebaseFirestore

class MemberDetailsActivity : AppCompatActivity()
{
	//!!TODO make it like a fragment where you click the member and can swipe down the view
	//with like a tab at the bottom to swipe it back up with persisting and changing info depending on the source member
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
		val memberID = intent.getStringExtra("memberID")
		if(docPath == null || memberID == null)
			activityManager.backToHome(this)
		else
		{
			binding.btnClose.setOnClickListener {
				finish()
				activityManager.startActivity(TreeActivity::class.java, docPath)
			}
			binding.btnEdit.setOnClickListener {
				activityManager.startActivity(EditMemberActivity::class.java, docPath)
			}
			populateMember(docPath, memberID)
		}
	}
	
	private fun populateMember(docPath: String, memberID: String)
	{
		val firebase = FirebaseFirestore.getInstance()
		firebase.document(docPath).get()
			.addOnSuccessListener {
				val member = it.toObject(FamilyTree::class.java)?.findMemberByID(memberID)
				if(member == null)
				{
					Toast.makeText(this, "Error, no such member", Toast.LENGTH_SHORT).show()
					activityManager.startActivity(TreeActivity::class.java, docPath)
				}
				else
				{
					// ? bind member details to activity views
					binding.txtBirthday.text = member.getBirthday() ?: "-"
					binding.txtDeathday.text = member.getDeathday() ?: "-"
					binding.txtBiography.text = member.biography ?: "-"
					binding.txtAge.text = "${member.getAge()} years old"
					binding.imgPortrait.setImageDrawable(member.image
					                                     ?: ResourcesCompat.getDrawable(resources, R.drawable.user, theme))
				}
			}
	}
}