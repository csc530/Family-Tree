package com.csc530.familytree.models

import java.util.*

/**
 * Node for BalkanJSTree.
 *
 * @property id The id of the node.
 * @property pids [Array] of ids of the partner nodes
 * @property mid The id of the mother node.
 * @property fid The id of the father node.
 * @property name The name of the node.
 * @property gender gender of node; male, female, or undefined
 * @property img href to node's image
 * @constructor Create [Node]
 */
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
		val name: String? = null,
		val gender: String? = null,
		/**The location of family member image*/
		val img: String? = null
)
{
	/**
	 * Convert node to [String]
	 * @return The name of the node back and their mom and dad ids
	 */
	override fun toString(): String
	{
		return "Node: $name-$id\n\tmom = $mid\n\tdad = $fid"
	}
}