package com.csc530.familytree

class Tree(
		var name: String,
		var creator: String,
		var contributors: Array<String>,
		var members: List<Member>
) {
}