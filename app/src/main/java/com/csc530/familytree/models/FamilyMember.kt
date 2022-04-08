package com.csc530.familytree.models

import android.net.Uri
import com.google.firebase.firestore.Exclude
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

/**
 * Holds the string value of valid sexes for a family member
 * @constructor Create empty constructor for sex enum
 */
enum class SexEnum
{
	UNKNOWN,
	MALE,
	FEMALE
}

/**
 * A Family member.
 *
 * @property firstName
 * @property lastName
 * @property birthEpochDay family member's birthdate, in Epoch days
 * @property deathEpochDay family member's date of death, in Epoch days
 * @property image [String] url to image depicting the family member
 * @property id unique id of the member within the firestore database
 * @property mother unique id of the mother of the member within the firestore database
 * @property father unique id of the father of the member within the firestore database
 * @property sex [SexEnum] of the member
 * @constructor Create [FamilyMember]
 */
data class FamilyMember(
		var firstName: String? = "????",
		var lastName: String? = "????",
		var birthEpochDay: Long? = null,
		var deathEpochDay: Long? = null,
		var image: String? = null,
		var id: String? = null,
		var mother: String? = null,
		var father: String? = null,
		var sex: SexEnum = SexEnum.UNKNOWN
)
{
	companion object Constants
	{
		/**
		 * A null id that no family member should ever have or be set to
		 * represent no family member
		 */
		const val NULL_ID = "-1 null nope naddadada-ID"
	}
	/**
	 * A [HashSet] of all the [FamilyMember]s children
	 */
	@get:Exclude
	@field:Exclude
	
	val children = HashSet<String>()
	
	/**
	 * A [HashSet] of all the [FamilyMember]s partners
	 */
	@get:Exclude
	@field:Exclude
	val partners = HashSet<String>()
	
	/**
	 * Get image uri.
	 *
	 * @return [Uri] of image or null if no image is set
	 */
	@Exclude
	fun getImageUri(): Uri?
	{
		return Uri.parse(image ?: return null)
	}
	
	/**
	 * String representation of the family member
	 *
	 * @return The first and last name of the family member with their age appended if valid
	 */
	override fun toString(): String
	{
		return "$firstName $lastName ${
			if(getAge() != -1)
				": ${getAge()}"
			else
				""
		}"
	}
	
	/**
	 * Gets the birthdate of the family member in the form of a [LocalDate]
	 *
	 * @return [LocalDate] or null if no birthdate is set
	 */
	@Exclude
	fun getBirthDate(): LocalDate?
	{
		if(birthEpochDay != null)
			return LocalDate.ofEpochDay(birthEpochDay!!)
		return null
	}
	
	/**
	 * Gets the death date of the family member in the form of a [LocalDate]
	 *
	 * @return [LocalDate] or null if no death date is set
	 */
	@Exclude
	fun getDeathDate(): LocalDate?
	{
		if(deathEpochDay != null)
			return LocalDate.ofEpochDay(deathEpochDay!!)
		return null
	}
	
	/**
	 * Get birthday of the family member in the form of a [String]
	 *
	 * @return [String] of the family member's birthdate or null if no birthdate is set
	 */
	@Exclude
	fun getBirthday(): String?
	{
		return getBirthDate()?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
	}
	
	/**
	 * Get deathday of the family member in the form of a [String]
	 *
	 * @return [String] of the family member's death date or null if no death date is set
	 */
	fun getDeathday(): String?
	{
		return getDeathDate()?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
	}
	
	/**
	 * Get age of the family member in the form of a [Int]
	 * Their age is calculated by subtracting their birthdate from the current date
	 * or their death date if they have died
	 *
	 * @return [Int] of the family member's age or -1 if no birthdate is set
	 */
	fun getAge(): Int
	{
		if(getBirthDate() == null) return -1
		return Period.between(getBirthDate(), getDeathDate() ?: LocalDate.now()).years
	}
	
	/**
	 * Creates a BalkanJSTree compatible node object
	 * Used to accurately generate a family tree member using BalkanJSTree
	 *
	 * @return [Node] or null if no id is set
	 */
	fun toNode(): Node?
	{
		val id = id ?: return null
		val pid: Array<String> = partners.toTypedArray()
		val mid = mother
		val fid = father
		val gender = sex.name.lowercase(Locale.getDefault())
		val img = image
		return Node(id, pid, mid, fid, getFullName(), gender, img)
	}
	
	/**
	 * Get full name of the member
	 *
	 * @return first name and last name separated by a space
	 */
	fun getFullName(): String
	{
		return "$firstName $lastName".trim()
	}
	
}