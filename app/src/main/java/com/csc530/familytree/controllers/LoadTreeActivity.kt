package com.csc530.familytree.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.csc530.familytree.databinding.ActivityLoadTreeBinding
import com.csc530.familytree.views.FamilyTreeAdapter
import com.csc530.familytree.views.FamilyTreeViewModel

class LoadTreeActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoadTreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadTreeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val familyTreeViewModel: FamilyTreeViewModel by viewModels()
        familyTreeViewModel.getFamilyTrees().observe(this){ familyTrees->
            binding.recyclerTree.adapter = FamilyTreeAdapter(this, familyTrees){familyTree, view ->
                val intent = Intent(this, TreeActivity::class.java)
                intent.putExtra("docPath", "${familyTree.name}-${familyTree.creator}-${familyTree.id}")
            }
        }
    }
}