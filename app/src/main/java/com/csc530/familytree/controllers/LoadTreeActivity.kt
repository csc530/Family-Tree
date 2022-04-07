package com.csc530.familytree.controllers

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityLoadTreeBinding
import com.csc530.familytree.models.ActivityManager
import com.csc530.familytree.views.FamilyTreeAdapter
import com.csc530.familytree.views.FamilyTreeViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class LoadTreeActivity : AppCompatActivity()
{
	lateinit var binding: ActivityLoadTreeBinding
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		
		
		binding = ActivityLoadTreeBinding.inflate(layoutInflater)
		setContentView(binding.root)
		val activityManager = ActivityManager(this)
		binding.fabBackToLaunch.setOnClickListener {
			activityManager.startActivity(LaunchActivity::class.java)
		}
		
		val familyTreeViewModel: FamilyTreeViewModel by viewModels()
		familyTreeViewModel.getFamilyTrees().observe(this) { familyTrees ->
			binding.recyclerTree.adapter = FamilyTreeAdapter(this, familyTrees, onTreeClick(false), onTreeClick(true))
			{ familyTree, view ->
				// * show dialog to change name of selected family Tree
				val input = EditText(this)
				input.setText(familyTree.name)
				AlertDialog.Builder(this)
					.setTitle("Change Name")
					.setView(input)
					.setPositiveButton("OK") { _, _ ->
						// ? validate name
						if(input.text.isBlank())
							Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
						else
						{
							// ? Delete old document with unchanged name
							val db = FirebaseFirestore.getInstance()
								.collection("Trees")
							db.document(familyTree.generateDocId())
								.delete()
							// ? Create new document with the changed name
							familyTree.name = input.text.toString()
							db.document(familyTree.generateDocId())
								.set(familyTree)
							Snackbar.make(view, "Name changed", Snackbar.LENGTH_LONG).show()
						}
					}
					.setNegativeButton("Cancel") { _, _ ->
						Snackbar.make(view, "Name not changed", Snackbar.LENGTH_LONG).show()
					}
					.show()
			}
		}
		
	}
	
	private fun onTreeClick(delete: Boolean): FamilyTreeAdapter.FamilyTreeClickListener
	{
		if(!delete)
			return FamilyTreeAdapter.FamilyTreeClickListener { familyTree, _ ->
				val docPath = familyTree.generateDocPath()
				ActivityManager.launchActivity(this, TreeActivity::class.java, docPath)
			}
		else
			return FamilyTreeAdapter.FamilyTreeClickListener { familyTree, _ ->
				val docPath = familyTree.generateDocPath()
				AlertDialog.Builder(this)
					.setTitle("Are you sure you want to delete ${familyTree.name}?")
					.setPositiveButton("Yes") { _, _ ->
						FirebaseFirestore.getInstance().document(docPath).delete()
							.addOnSuccessListener {
								Snackbar.make(binding.root, "Successfully Deleted", Snackbar.LENGTH_SHORT).show()
							}
							.addOnFailureListener {
								Snackbar.make(binding.root, "Failed to Delete", Snackbar.LENGTH_SHORT).show()
								Log.e("Delete", it.message ?: it.localizedMessage, it)
							}
					}
					.setNegativeButton("No") { _, _ ->
						Snackbar.make(binding.root, "Deletion Cancelled", Snackbar.LENGTH_SHORT).show()
					}
					.show()
			}
	}
}