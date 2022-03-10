package com.csc530.familytree.models

import android.graphics.drawable.Drawable
import java.time.LocalDate
import java.time.Period
import java.util.*

class Member(
		var firstName: String? = "????",
		var lastName: String? = "????",
		var birthEpochDay: Long? = null,
		var deathEpochDay: Long? = null,
		var comments: Array<String>? = null,
		var image: Drawable? = null,
		var uid: String? = null
) {
	lateinit var id: String
	var birthdate: LocalDate? = null
		set(value) {
			birthEpochDay = value?.toEpochDay()
			age = Period.between(birthdate, deathdate).toTotalMonths()
			field = value
		}
	var deathdate: LocalDate? = null
		set(value) {
			deathEpochDay = value?.toEpochDay()
			age = Period.between(birthdate, deathdate).toTotalMonths()
			field = value
		}
	var parents: ArrayList<Member> = ArrayList<Member>()
	var kids: ArrayList<Member> = ArrayList<Member>()
	var partners: ArrayList<Member> = ArrayList<Member>()
	var age: Long = -1
	
	init {
		if (birthEpochDay != null)
			birthdate = LocalDate.ofEpochDay(birthEpochDay!!)
		if (deathEpochDay != null)
			deathdate = LocalDate.ofEpochDay(deathEpochDay!!)
		age = Period.between(birthdate, deathdate).toTotalMonths()
	}
	
	fun getAgePeriod(): Period {
		return Period.between(birthdate, deathdate)
	}
	
	fun getAge(): Int {
		return Period.between(birthdate, deathdate).years
	}
	
	fun isDead(): Boolean {
		return deathdate != null
	}
}