package com.csc530.familytree.models

import android.graphics.drawable.Drawable
import java.time.LocalDate
import java.time.Period

data class Member(
		var firstName: String? = "????",
		var lastName: String? = "????",
		var birthEpochDay: Long? = null,
		var deathEpochDay: Long? = null,
		var comments: Array<String>? = null,
		var image: Drawable? = null,
		var uid: String? = null
)
{
	lateinit var id: String
	
	var parents: ArrayList<Member> = ArrayList<Member>()
	var kids: ArrayList<Member> = ArrayList<Member>()
	var partners: ArrayList<Member> = ArrayList<Member>()
	
	init
	{
	}
	
	private fun getBirthDate(): LocalDate?
	{
		if(birthEpochDay != null)
			return LocalDate.ofEpochDay(birthEpochDay!!);
		return null
	}

	private fun getDeathDate(): LocalDate?
	{
		if(deathEpochDay != null)
			return LocalDate.ofEpochDay(deathEpochDay!!);
		return null
	}

	fun getAge(): Int
	{
		if(getBirthDate() == null) return -1
		return Period.between(getBirthDate(), getDeathDate() ?: LocalDate.now()).years
	}
	
	fun isDead(): Boolean
	{
		return getDeathDate() != null
	}
}