package com.csc530.familytree.models

import com.google.firebase.Timestamp
import com.google.gson.Gson

/**
 * Family tree
 *
 * @property name name of the family tree
 * @property creator creator of the family tree [String] user id
 * @property members list of [FamilyMember]s in the family tree
 * @property created timestamp of when the family tree was created
 * @property lastModified timestamp of when the family tree or any of it's members was last modified
 * @property image image of the family tree
 * @property id unique identifier of the family tree when uploaded to the database
 * @constructor Create [FamilyTree]
 */
class FamilyTree(
		var name: String = "",
		var creator: String = "",
		var members: ArrayList<FamilyMember> = ArrayList(),
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
	
	/**
	 * Generate document path for the family tree
	 *
	 * @return document path for the family tree from firestore database root
	 * @see com.google.firebase.firestore.FirebaseFirestore.collection
	 * @throws Exception if the family tree id is null or empty
	 */
	fun generateDocPath(): String
	{
		return "Trees/" + generateDocId()
	}
	
	/**
	 * Generate document id for the family tree
	 *  that will remove any illegal or restricted characters for firestore
	 *
	 * @return document id for the family tree within firestore database
	 * @see com.google.firebase.firestore.FirebaseFirestore.collection
	 * @throws Exception if the family tree id is null or empty
	 */
	fun generateDocId(): String
	{
		if(id.isNullOrEmpty())
			throw Exception("FamilyTree has not been saved to the database yet")
		val safeName = this.name
			.replace("/", "", true)
			.replace("\\", "", true)
			.replace(" ", "_", true)
			.replace("\n", "__", true)
			.replace("\r", "__", true)
			.replace("\t", "--", true)
			.trim()
		return "${safeName}-$creator-${id}"
	}
	
	/**
	 * Populate relationships for each member in the family tree; partners and children
	 */
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
	fun findMemberByID(id: String?): FamilyMember?
	{
		for(member in members)
			if(member.id == id)
				return member
		return null
	}
	
	/**
	 * Get members by id within family tree
	 *
	 * @param ids Ids of desired family members
	 * @return [FamilyMember]s or empty list if not found
	 */
	fun getMembersByID(ids: Set<String>): List<FamilyMember>
	{
		val list = ArrayList<FamilyMember>()
		for(id in ids)
			list += findMemberByID(id) ?: continue
		return list
	}
	
	/**
	 * Populate children for each member in the family tree
	 */
	private fun populateChildren()
	{
		// clear previous children sets for each member
		members.forEach { member ->
			member.children.clear()
		}
		// * for each member if they have a parent; mother or father, assign that parent
		// * this child; the current member, in their children set
		for(member in members)
		{
			if(member.mother != null)
				findMemberByID(member.mother!!)?.children?.add(member.id ?: continue)
			if(member.father != null)
				findMemberByID(member.father!!)?.children?.add(member.id ?: continue)
		}
	}
	
	/**
	 * Populate partners for each member in the family tree
	 */
	private fun populatePartners()
	{
		// clear previous partners sets for each member
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
	
	/**
	 * A [String] representation of the family tree
	 * @return [String] of the family tree name and creator and date created. Followed by a json String of it's members
	 */
	override fun toString(): String
	{
		return "FamilyTree(name='$name', creator='$creator', created=$created)\n${Gson().toJson(members)}"
	}
}