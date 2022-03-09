package com.csc530.familytree.controllers

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.csc530.familytree.databinding.ActivityEditMemberBinding
import com.csc530.familytree.models.Member
import java.time.LocalDate
import java.util.*


class EditMemberActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityEditMemberBinding
	private val locale = Locale.getDefault();
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = ActivityEditMemberBinding.inflate(layoutInflater)
		setContentView(binding.root)
		//? show datepicker when birth or date date is selected
		binding.edtDD.setOnFocusChangeListener { deathDate, hasFocus ->
			deathDate.isEnabled = !hasFocus
			if(hasFocus)
				setDate(deathDate as EditText, "Death date")
		}
		binding.edtBD.setOnFocusChangeListener { birthDate, hasFocus ->
			birthDate.isEnabled = !hasFocus
			if(hasFocus)
				setDate(birthDate as EditText, "Birth date")
		}
		binding.btnCnfm.setOnClickListener {
			val firstName = binding.edtFName.text.toString()
			val lastName = binding.edtLName.text.toString()
			val birthday = LocalDate.parse(binding.edtBD.text)
			val deathDate = LocalDate.parse(binding.edtDD.text)
			val comments = binding.taOther.text.toString()
			val member = Member(firstName, lastName, birthday, deathDate)
			val intent = Intent(this, TreeActivity::class.java)
			// TODO:  find a way to transfer the new member information across intents
			//!! or simply update db/write to file over the previous member and reload whole tree when returning
//			intent.putExtra("member", member)
			startActivity(intent)
		}
	}
	
	private fun setDate(value: EditText, title: String)
	{
		val date = DatePickerDialog(this)
		date.updateDate(LocalDate.now().year, LocalDate.now().monthValue, LocalDate.now().dayOfMonth)
		date.setTitle(title)
		date.datePicker.maxDate = Date().toInstant().toEpochMilli()
		date.setButton(DialogInterface.BUTTON_POSITIVE,"Confirm") { _, _ ->
			val year = date.datePicker.year
			val month = date.datePicker.month
			val day = date.datePicker.dayOfMonth
			value.setText(LocalDate.of(year, month, day).toString())
		}
		date.setButton(DialogInterface.BUTTON_NEUTRAL, "Clear"){_,_->
			value.text = null
		}
		date.setOnDismissListener {
			value.clearFocus()
		}
		date.show()
	}
}