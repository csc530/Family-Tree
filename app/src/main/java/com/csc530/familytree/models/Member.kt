package com.csc530.familytree.models

import android.app.DatePickerDialog
import android.graphics.drawable.Drawable
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.DateTimePrinter
import java.util.*
import kotlin.collections.ArrayList


class Member(
		var firstName: String? = "????",
		var lastName: String? = "????",
		var birthEpochDay: Long? = null,
		var deathEpochDay: Long? = -1,
		var comments: Array<String>? = null,
		var image: Drawable? = null,
		var uid: String?=null
)
{
	lateinit var id: String
	lateinit var birthdate: LocalDate
	lateinit var deathdate: org.joda.time.LocalDate
	var parents: ArrayList<Member> = ArrayList<Member>()
	var kids: ArrayList<Member> = ArrayList<Member>()
	var partners: ArrayList<Member> = ArrayList<Member>()
	var age: Int = -1
	
	init
	{
		birthdate = LocalDate.parse(birthEpochDay)
	}
	
	fun isDead(): Boolean
	{
		return deathDate != null
	}
}