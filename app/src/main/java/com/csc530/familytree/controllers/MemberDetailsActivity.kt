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
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


class MemberDetailsActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityMemberDetailsBinding
	private lateinit var activityManager: ActivityManager
	private val firebase = FirebaseFirestore.getInstance()
	
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
			return activityManager.backToHome("Could not find member")
		
		populateMemberDetails(docPath, memberId)
		
		binding.btnClose.setOnClickListener {
			finish()
		}
		binding.btnEdit.setOnClickListener {
			activityManager.startActivity(EditMemberActivity::class.java, docPath, memberId)
		}
		binding.fabDelete.setOnClickListener {
			// * show a dialog to confirm deletion
			MaterialAlertDialogBuilder(this)
				.setTitle("Delete Member")
				.setMessage("Are you sure you want to delete this member?")
				.setPositiveButton("Yes") { _, _ ->
					// * delete the member
					deleteMember(docPath, memberId)
				}
				.setNegativeButton("No") { _, _ ->
					// * do nothing
				}
				.show()
		}
	}
	
	/**
	 * Delete member from the database
	 *
	 *
	 * @param docPath Document path of the family tree to delete the member from
	 * @param memberId ID of the member to delete
	 */
	private fun deleteMember(docPath: String, memberId: String)
	{
		firebase.document(docPath).get()
			.addOnSuccessListener { result ->
				// * get the family tree; if it doesn't exist, return
				val familyTree = result.toObject(FamilyTree::class.java) ?: return@addOnSuccessListener
				// ? edit the family tree remove all references to the member (the member and if they are a mother or father); then save the tree
				for(member in familyTree.members)
					when(memberId)
					{
						member.id                    -> familyTree.members.remove(member)
						member.mother, member.father ->
						{
							member.mother = null
							member.father = null
						}
					}
				
				// * save the family tree; update the data in firestore
				firebase.document(docPath)
					.update("lastModified", Timestamp.now(),
					        "members", familyTree.members)
					.addOnSuccessListener {
						Toast.makeText(this, "Member deleted", Toast.LENGTH_SHORT).show()
						finish()
					}
					.addOnFailureListener {
						Toast.makeText(this, "Failed to delete member", Toast.LENGTH_SHORT).show()
					}
			}
	}
	
	/**
	 * Populate the member details within the activity
	 *
	 * @param docPath Document path of the family tree to get the member from
	 * @param memberId ID of the member whose details to populate the activity
	 */
	private fun populateMemberDetails(docPath: String, memberId: String)
	{
		FamilyTreeViewModel(docPath).getFamilyTree().observe(this) { familyTree ->
			val member = familyTree?.findMemberByID(memberId)
			// ? if the member doesn't exist, return
			if(member == null)
			{
				Toast.makeText(this, "Error, no such member", Toast.LENGTH_SHORT).show()
				finish()
				return@observe activityManager.startActivity(TreeActivity::class.java, docPath)
			}
			familyTree.populateRelationships()
			
			// ? bind member details to activity views
			binding.txtFullName.text = member.getFullName()
			binding.txtBirthday.text = member.getBirthday() ?: "-"
			binding.txtDeathday.text = member.getDeathday() ?: "-"
			if(member.getAge() != -1)
				binding.txtAge.text = getString(R.string.age_placeholder, member.getAge())
			else
				binding.txtAge.text = "-"
			
			Picasso.get()
				.load(member.getImageUri())
				.placeholder(R.drawable.user)
				.into(binding.imgPortrait)
			
			//set up children and partner to display total number
			binding.txtChildren.text = getString(R.string.children_placeholder, member.children.size)
			binding.txtPartners.text = getString(R.string.partners_placeholder, member.partners.size)
			
			binding.txtSex.text = member.sex.toString()
			
			// * when the text is clicked, display each child or partner in a recycler view
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
	
	/**
	 * Displays a list of members in a recycler view dialog
	 *
	 * @param members Members to display
	 * @param title Title of the list
	 * @param docPath Document path of the family tree to get the member from
	 */
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
	
	/**
	 * The on click handler for each item in the recycler view dialog
	 *
	 * @param docPath Document path of the family tree to send to the next activity
	 * @return [(FamilyMember, View) -> Unit] A lambda containing the on click handler
	 */
	private fun toMember(docPath: String): (FamilyMember, View) -> Unit = { familyMember: FamilyMember, _: View ->
		val intent = Intent(this, MemberDetailsActivity::class.java)
		intent.putExtra("memberId", familyMember.id)
		activityManager.startActivity(intent, docPath)
	}
}