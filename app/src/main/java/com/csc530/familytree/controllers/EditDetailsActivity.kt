package com.csc530.familytree.controllers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityEditDetailsBinding

class EditDetailsActivity : AppCompatActivity() {
	//!!TODO make it like a fragment where you click the member and can swipe down the view
	//with like a tab at the bottom to swipe it back up with persisting and changing info depending on the source member
	private lateinit var binding: ActivityEditDetailsBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityEditDetailsBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}
}