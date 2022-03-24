package com.csc530.familytree.models

import android.graphics.drawable.Drawable
import java.text.DateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

data class FamilyMember(
		var firstName: String? = "????",
		var lastName: String? = "????",
		var birthEpochDay: Long? = null,
		var deathEpochDay: Long? = null,
		var biography: String? = null,
		var image: Drawable? = null,
		var id: String? = null,
		var mom: String? = null,
		var dad: String? = null,
		//TODO make String enum
		var sex: String = "?",
)
{
	companion object Constants
	{
		const val NULL_ID = "-1 null nope naddadada-ID"
	}
	
	override fun toString(): String
	{
		return "$firstName $lastName ${
			if(getAge() != -1)
				": ${getAge()}"
			else
				""
		}"
	}
	
	var kids: ArrayList<String> = ArrayList<String>()
	var partners: ArrayList<String> = ArrayList<String>()
	
	fun getBirthDate(): LocalDate?
	{
		if(birthEpochDay != null)
			return LocalDate.ofEpochDay(birthEpochDay!!);
		return null
	}
	
	fun getDeathDate(): LocalDate?
	{
		if(deathEpochDay != null)
			return LocalDate.ofEpochDay(deathEpochDay!!);
		return null
	}
	
	fun getBirthday(): String?
	{
		return getBirthDate()?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
	}
	
	fun getDeathday(): String?
	{
		return getDeathDate()?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
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
	
	fun toNode(): Node?
	{
		val id = id ?: return null
		val pid: Array<String> = partners.toTypedArray()
		val mid = mom
		val fid = dad
		val gender = sex
		val img = image
		return Node(id, pid, mid, fid, getFullName())
	}
	
	fun getFullName(): String
	{
		return "$firstName $lastName".trim()
	}
}