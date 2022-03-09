package com.csc530.familytree.models

import android.app.DatePickerDialog
import android.graphics.drawable.Drawable
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class Member(
		var firstName: String? = "????",
		var lastName: String? = "????",
		var birthday: DateTime? = null,
		var deathDate: DateTime? = null,
		var comments: Array<String>? = null,
		var image: Drawable? = null,
)
{
	var parents: ArrayList<Member> = ArrayList<Member>()
	var kids: ArrayList<Member> = ArrayList<Member>()
	var partners: ArrayList<Member> = ArrayList<Member>()
	var age: Int = -1
	
	init
	{
//		// ? no methods in any time/date class I found so I got to some conversions myself :(
//		age = if(deathDate != null && birthday != null)
//		/* ? .25 for the amount of leap years to normal ones*/
//			((deathDate!!.toEpochDay() - birthday!!.toEpochDay()) / 365.25).roundToInt()
//		else if(birthday != null)
//			(birthday!!.toEpochDay() - LocalDate.now().toEpochDay() / 365.25).roundToInt()
//		else
//			-1;
	}
	
	fun isDead(): Boolean
	{
		return deathDate != null
	}
}