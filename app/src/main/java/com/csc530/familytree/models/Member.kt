package com.csc530.familytree.models

import android.graphics.drawable.Drawable
import java.time.LocalDate
import java.time.Period

data class Member(
		var firstName: String? = "????",
		var lastName: String? = "????",
		private var birthdate: LocalDate? = null,
		private var deathdate: LocalDate? = null,
		var comments: Array<String>? = null,
		var image: Drawable? = null,
		var uid: String? = null
)
{
	lateinit var id: String
	var birthEpochDay: Long? = null
		set(value)
		{
			if(value != null)
				birthdate = LocalDate.ofEpochDay(value)
			field = value
		}
	var deathEpochDay: Long? = null
		set(value)
		{
			if(value != null)
				deathdate = LocalDate.ofEpochDay(value)
			field = value
		}
	
	var parents: ArrayList<Member> = ArrayList<Member>()
	var kids: ArrayList<Member> = ArrayList<Member>()
	var partners: ArrayList<Member> = ArrayList<Member>()
	
	init
	{
		if(birthdate != null)
			birthEpochDay = birthdate!!.toEpochDay()
		if(deathdate != null)
			deathEpochDay = deathdate!!.toEpochDay()
	}
	
	fun getAgePeriod(): Period?
	{
		if(birthdate == null) return null
		return Period.between(birthdate, deathdate ?: LocalDate.now())
	}
	
	fun getAge(): Int
	{
		if(birthdate == null) return -1
		return Period.between(birthdate, deathdate ?: LocalDate.now()).years
	}
	
	fun isDead(): Boolean
	{
		return deathdate != null
	}
}