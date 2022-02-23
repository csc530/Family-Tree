package com.csc530.familytree.controllers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityTreeBinding

class TreeActivity : AppCompatActivity() {
	private lateinit var binding: ActivityTreeBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityTreeBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}
}