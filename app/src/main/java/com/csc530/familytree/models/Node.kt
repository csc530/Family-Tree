package com.csc530.familytree.models

class Node(
		/** property is mandatory.*/
		val id: String = "-1",
		/**are the partner ids, represents connection between two partners (wife and husband).*/
		val pids: Array<String>? = null,
		/**mother id.*/
		val mid: String? = null,
		/**father id.*/
		val fid: String? = null,
		/**Full name of the family member*/
		val name:String?=null,
)
{
	override fun toString(): String
	{
		return "Node: $name-$id\n\tmom = $mid\n\tdad = $fid"
	}
}