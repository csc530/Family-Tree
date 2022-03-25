package com.csc530.familytree.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.csc530.familytree.databinding.ActivityLoadTreeBinding
import com.csc530.familytree.models.ActivityManager
import com.csc530.familytree.views.FamilyTreeAdapter
import com.csc530.familytree.views.FamilyTreeViewModel

class LoadTreeActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoadTreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadTreeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val activityManager = ActivityManager(this)
        binding.fabBackToLaunch.setOnClickListener {
            activityManager.startActivity(LaunchActivity::class.java)
        }
        
        val familyTreeViewModel: FamilyTreeViewModel by viewModels()
        familyTreeViewModel.getFamilyTrees().observe(this){ familyTrees->
            binding.recyclerTree.adapter = FamilyTreeAdapter(this, familyTrees){familyTree, view ->
                val intent = Intent(this, TreeActivity::class.java)
                val docPath = "Trees/${familyTree.name}-${familyTree.creator}-${familyTree.id}"
                intent.putExtra("docPath", docPath)
                startActivity(intent)
            }
        }
    }
}