package com.csc530.familytree.controllers

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
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
	private lateinit var binding: ActivityLoadTreeBinding
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = ActivityLoadTreeBinding.inflate(layoutInflater)
		setContentView(binding.root)
		val activityManager = ActivityManager(this)
		
		binding.fabBackToLaunch.setOnClickListener {
			activityManager.startActivity(LaunchActivity::class.java)
		}
		
		FamilyTreeViewModel().getFamilyTrees().observe(this) { familyTrees ->
			if(familyTrees == null) return@observe activityManager.backToHome("No Family Trees Found")
			
			// * setup recycler view adapter
			binding.recyclerTree.adapter = FamilyTreeAdapter(this, familyTrees, onTreeClick(false), onTreeClick(true))
			// * on click listener for the recycler view's item edit button
			{ familyTree, view ->
				// * show dialog to change name of selected family Tree
				// * setup edit text field to capture new tree name
				val input = EditText(this)
				input.setText(familyTree.name)
				// * setup dialog to show edit text field
				AlertDialog.Builder(this)
					.setTitle("Change Name")
					.setView(input)
					.setPositiveButton("OK") { _, _ ->
						// ? validate name
						if(input.text.isBlank())
							Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
						else
						{
							val db = FirebaseFirestore.getInstance().collection("Trees")
							
							// ? Delete old document with unchanged name
							db.document(familyTree.generateDocId()).delete()
							
							// ? Create new document with the changed name
							familyTree.name = input.text.toString()
							db.document(familyTree.generateDocId()).set(familyTree)
							
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
	
	/**
	 * On click listener for the Family Tree recycler view's
	 * Used if the recycler view item is clicked or it's delete button is clicked
	 * @param delete True if the delete button is clicked, false if the item is clicked
	 * @return On click listener for the Family Tree recycler view's item
	 */
	private fun onTreeClick(delete: Boolean): FamilyTreeAdapter.FamilyTreeClickListener
	{
		// * on click listener for the recycler view's item; navigate to the Family Tree activity
		if(!delete)
			return FamilyTreeAdapter.FamilyTreeClickListener { familyTree, _ ->
				val docPath = familyTree.generateDocPath()
				ActivityManager.launchActivity(this, TreeActivity::class.java, docPath)
			}
		else // ? on click listener for the recycler view's item's delete button; delete the Family Tree
			return FamilyTreeAdapter.FamilyTreeClickListener { familyTree, _ ->
				val docPath = familyTree.generateDocPath()
				// * show confirmation dialog
				AlertDialog.Builder(this)
					.setTitle("Are you sure you want to delete ${familyTree.name}?")
					// * on positive button click, delete the Family Tree
					.setPositiveButton("Yes") { _, _ ->
						FirebaseFirestore.getInstance().document(docPath).delete()
							.addOnSuccessListener {
								Snackbar.make(binding.root, "Deleted ${familyTree.name}", Snackbar.LENGTH_LONG).show()
							}
							.addOnFailureListener { e ->
								Log.e("LoadTreeActivity", "Failed to delete ${familyTree.name}", e)
								Snackbar.make(binding.root, "Failed to delete ${familyTree.name}", Snackbar.LENGTH_LONG)
									.show()
							}
					}
					.setNegativeButton("No") { _, _ ->
						Snackbar.make(binding.root, "Deletion Cancelled", Snackbar.LENGTH_SHORT).show()
					}
					.show()
			}
	}
}