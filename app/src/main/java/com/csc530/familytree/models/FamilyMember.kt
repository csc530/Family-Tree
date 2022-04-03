package com.csc530.familytree.models

import android.net.Uri
import com.google.firebase.firestore.Exclude
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

enum class SexEnum
{
	UNKNOWN,
	MALE,
	FEMALE
}

data class FamilyMember(
		var firstName: String? = "????",
		var lastName: String? = "????",
		var birthEpochDay: Long? = null,
		var deathEpochDay: Long? = null,
		//		var biography: String? = null,
		var image: String? = null,
		var id: String? = null,
		var mother: String? = null,
		var father: String? = null,
		var sex: SexEnum = SexEnum.UNKNOWN
)
{
	companion object Constants
	{
		const val NULL_ID = "-1 null nope naddadada-ID"
	}
	
	@get:Exclude
	@field:Exclude
	val children = HashSet<String>()
	
	@get:Exclude
	@field:Exclude
	val partners = HashSet<String>()
	
	@Exclude
	fun getImageUri(): Uri?
	{
		return Uri.parse(image?: return null)
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
	
	@Exclude
	fun getBirthDate(): LocalDate?
	{
		if(birthEpochDay != null)
			return LocalDate.ofEpochDay(birthEpochDay!!);
		return null
	}
	
	@Exclude
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
		val mid = mother
		val fid = father
		val gender = sex.name.lowercase(Locale.getDefault())
		val img = image
		return Node(id, pid, mid, fid, getFullName(), gender)
	}
	
	
	fun getFullName(): String
	{
		return "$firstName $lastName".trim()
	}
}