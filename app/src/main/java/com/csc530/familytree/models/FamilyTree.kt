package com.csc530.familytree.models

import com.google.firebase.Timestamp
import com.google.gson.Gson

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
	init
	{
		populateRelationships()
	}
	
	fun populateRelationships()
	{
		populateChildren()
		populatePartners()
	}
	
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
	
	fun getMembersByID(ids: List<String>): List<FamilyMember>
	{
		val list = ArrayList<FamilyMember>()
		for(id in ids)
			list += findMemberByID(id) ?: continue
		return list
	}
	
	fun getMembersByID(ids: Set<String>): List<FamilyMember>
	{
		val list = ArrayList<FamilyMember>()
		for(id in ids)
			list += findMemberByID(id) ?: continue
		return list
	}
	
	private fun populateChildren()
	{
		members.forEach { member ->
			member.children.clear()
		}
		// * for each member if they have a parent, mother or father, assign that parent child said member
		for(member in members)
		{
			if(member.mother != null)
				findMemberByID(member.mother!!)?.children?.add(member.id ?: continue)
			if(member.father != null)
				findMemberByID(member.father!!)?.children?.add(member.id ?: continue)
		}
	}
	
	override fun toString(): String
	{
		return "FamilyTree(name='$name', creator='$creator', created=$created)\n${Gson().toJson(members)}"
	}
	
	private fun populatePartners()
	{
		for(member in members)
			member.partners.clear()
		
		// * for each member that has a child assign their mother a partner to the child's father and vice versa
		for(member in members)
			for(childId in member.children)
			{
				val child = findMemberByID(childId) ?: continue
				if(child.mother == member.id)
					member.partners.add(child.father ?: continue)
				else if(child.father == member.id)
					member.partners.add(child.mother ?: continue)
			}
	}
	
}