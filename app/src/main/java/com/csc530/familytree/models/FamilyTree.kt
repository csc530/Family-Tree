package com.csc530.familytree.models

import android.graphics.drawable.Drawable
import android.media.Image
import com.google.firebase.Timestamp

class FamilyTree(
		var name: String = "",
		var creator: String = "",
		var contributors: Array<String>? = null,
		var members: ArrayList<FamilyMember> = ArrayList<FamilyMember>(),
		var created: Timestamp = Timestamp.now(),
		var lastModified: Timestamp = Timestamp.now(),
		var image: Int?=null,
		var id:String?=null
) {}