package com.csc530.familytree.controllers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityInfoBinding
import com.csc530.familytree.models.ActivityManager

class InfoActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityInfoBinding
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = com.csc530.familytree.databinding.ActivityInfoBinding.inflate(layoutInflater)
		setContentView(binding.root)
		binding.fabHome.setOnClickListener {
			ActivityManager.launchActivity(this, LaunchActivity::class.java)
		}
	}
}