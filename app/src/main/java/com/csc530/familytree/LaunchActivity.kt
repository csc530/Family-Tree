package com.csc530.familytree

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.csc530.familytree.databinding.ActivityLaunchBinding

class LaunchActivity : AppCompatActivity() {
	private lateinit var binding: ActivityLaunchBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_launch)
		
		binding = ActivityLaunchBinding.inflate(layoutInflater)
		setContentView(binding.root)
		//Switch intents on button click
		binding.btnNewTree.setOnClickListener {
			val intent = Intent(this, TreeActivity::class.java)
			startActivity(intent)
		}
		binding.btnLoadTree.setOnClickListener {
			val intent = Intent(this, LoadTreeActivity::class.java)
			startActivity(intent)
		}
	}
}