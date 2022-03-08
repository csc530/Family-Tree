package com.csc530.familytree.models

import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*
import kotlin.math.roundToInt

//@RequiresApi(Build.VERSION_CODES.O)
class Member(
		var firstName: String,
		var lastName: String? = "",
		var birthday: LocalDate? = null,
		var deathDate: LocalDate? = null,
		var comments: Array<String>? = null,
		var image: Drawable? = null,
) {
	lateinit var parents: List<Member>
	lateinit var kids: TreeSet<Member>
	lateinit var partners: List<Member>
	var age: Int
	
	init {
		// ? no methods in any time/date class I found so I got to some conversions myself :(
		age = if(deathDate != null && birthday != null)
		/* ? .25 for the amount of leap years to normal ones*/
			((deathDate!!.toEpochDay() - birthday!!.toEpochDay()) / 365.25).roundToInt()
		else if(birthday != null)
			(birthday!!.toEpochDay() - LocalDate.now().toEpochDay() / 365.25).roundToInt()
		else
			-1;
	}
	
	fun isDead(): Boolean {
		return deathDate != null
	}
}