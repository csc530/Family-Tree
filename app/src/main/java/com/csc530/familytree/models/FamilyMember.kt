package com.csc530.familytree.models

import android.graphics.drawable.Drawable
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList

enum class SexEnum
{
	UNKNOWN,
	MALE,
	FEMALE
}

@IgnoreExtraProperties
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
		var sex: SexEnum = SexEnum.UNKNOWN
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
	
	private val kids: ArrayList<String> = ArrayList<String>()
	private val partners: ArrayList<String> = ArrayList<String>()
	fun getPartners(): List<String>
	{
		return partners
	}
	
	fun getKids(): List<String>
	{
		return kids
	}
	
	fun addPartner(id: String)
	{
		if(!partners.contains(id))
			partners.add(id)
	}
	
	fun addChild(id: String)
	{
		if(!kids.contains(id))
			kids.add(id)
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
		val mid = mom
		val fid = dad
		val gender = sex.name.lowercase(Locale.getDefault())
		val img = image
		return Node(id, pid, mid, fid, getFullName(), gender)
	}
	
	fun getFullName(): String
	{
		return "$firstName $lastName".trim()
	}
}