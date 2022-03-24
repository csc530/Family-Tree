package com.csc530.familytree.models

import com.google.firebase.Timestamp

class FamilyTree(
		var name: String = "",
		var creator: String = "",
		var contributors: Array<String>? = null,
		var members: ArrayList<FamilyMember> = ArrayList<FamilyMember>(),
		var created: Timestamp = Timestamp.now(),
		var lastModified: Timestamp = Timestamp.now(),
		var image: Int? = null,
		var id: String? = null
)
{
	/**
	 * Find  family member by id within family tree
	 *
	 * @param id Id of desired family member
	 * @return [FamilyMember] or null if not found
	 */
	fun findMemberByID(id: String): FamilyMember?
	{
		for(member in members)
			if(member.id == id)
				return member
		return null
	}
	
	fun getMembersByID(ids: List<String>): ArrayList<FamilyMember>
	{
		val list = ArrayList<FamilyMember>()
		for(id in ids)
			list += findMemberByID(id) ?: continue
		return list
	}
}