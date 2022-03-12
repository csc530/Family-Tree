package com.csc530.familytree.models

import android.graphics.drawable.Drawable
import java.time.LocalDate
import java.time.Period

data class FamilyMember(
		var firstName: String? = "????",
		var lastName: String? = "????",
		var birthEpochDay: Long? = null,
		var deathEpochDay: Long? = null,
		var comments: String? = null,
		var image: Drawable? = null,
		var id: String? = null
)
{
	
	var parents: ArrayList<FamilyMember> = ArrayList<FamilyMember>()
	var kids: ArrayList<FamilyMember> = ArrayList<FamilyMember>()
	var partners: ArrayList<FamilyMember> = ArrayList<FamilyMember>()
	
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