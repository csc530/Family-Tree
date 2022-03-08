package com.csc530.familytree.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.csc530.familytree.databinding.ActivityEditMemberBinding

class EditMemberActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityEditMemberBinding
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = ActivityEditMemberBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}
}