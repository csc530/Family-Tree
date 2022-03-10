package com.csc530.familytree.models

import android.widget.TimePicker
import com.google.firebase.Timestamp

class Tree(
		var name: String = "",
		var creator: String = "",
		var contributors: Array<String>? = null,
		var members: ArrayList<Member> = ArrayList<Member>(),
		var created: Timestamp = Timestamp.now(),
		var lastModified: Timestamp = Timestamp.now()
) {}