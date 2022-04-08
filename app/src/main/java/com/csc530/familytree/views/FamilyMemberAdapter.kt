package com.csc530.familytree.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.csc530.familytree.R
import com.csc530.familytree.models.FamilyMember
import com.squareup.picasso.Picasso

/**
 * Family member adapter for RecyclerView to display a list of family members.
 * showing their name and image
 * @property context the context of the activity
 * @property members the list of family members to display
 * @property itemListener the onItemClickListener for each of the adapters child views
 * @constructor Create [FamilyMemberAdapter] with the context and list of family members
 */
class FamilyMemberAdapter(val context: Context,
                          private val members: List<FamilyMember>,
                          private val itemListener: (FamilyMember, View) -> Unit)
	: RecyclerView.Adapter<FamilyMemberAdapter.FamilyMemberViewHolder>()
{
	
	inner class FamilyMemberViewHolder(view: View) : RecyclerView.ViewHolder(view)
	{
		val image: ImageView = itemView.findViewById(R.id.imgFamMemberPortrait)
		val fullName: TextView = itemView.findViewById(R.id.txtFamMemberName)
	}
	
	/**
	 * Called when RecyclerView needs a new [RecyclerView.ViewHolder] of the given type to represent
	 * an item.
	 *
	 *
	 * This new ViewHolder should be constructed with a new View that can represent the items
	 * of the given type. You can either create a new View manually or inflate it from an XML
	 * layout file.
	 *
	 *
	 * The new ViewHolder will be used to display items of the adapter using
	 * [.onBindViewHolder]. Since it will be re-used to display
	 * different items in the data set, it is a good idea to cache references to sub views of
	 * the View to avoid unnecessary [View.findViewById] calls.
	 *
	 * @param parent The ViewGroup into which the new View will be added after it is bound to
	 * an adapter position.
	 * @param viewType The view type of the new View.
	 *
	 * @return A new ViewHolder that holds a View of the given view type.
	 * @see .getItemViewType
	 * @see .onBindViewHolder
	 */
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyMemberViewHolder
	{
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_family_member, parent, false)
		return FamilyMemberViewHolder(view)
	}
	
	/**
	 * Returns the total number of items in the data set held by the adapter.
	 *
	 * @return The total number of items in this adapter.
	 */
	override fun getItemCount(): Int
	{
		return members.size
	}
	
	/**
	 * Called by RecyclerView to display the data at the specified position. This method should
	 * update the contents of the [RecyclerView.ViewHolder.itemView] to reflect the item at the given
	 * position.
	 *
	 *
	 * Note that unlike [android.widget.ListView], RecyclerView will not call this method
	 * again if the position of the item changes in the data set unless the item itself is
	 * invalidated or the new position cannot be determined. For this reason, you should only
	 * use the `position` parameter while acquiring the related data item inside
	 * this method and should not keep a copy of it. If you need the position of an item later
	 * on (e.g. in a click listener), use [RecyclerView.ViewHolder.getAdapterPosition] which will
	 * have the updated adapter position.
	 *
	 * Override [.onBindViewHolder] instead if Adapter can
	 * handle efficient partial bind.
	 *
	 * @param holder The ViewHolder which should be updated to represent the contents of the
	 * item at the given position in the data set.
	 * @param position The position of the item within the adapter's data set.
	 */
	override fun onBindViewHolder(holder: FamilyMemberViewHolder, position: Int)
	{
		val familyMember = members[position]
		with(holder) {
			//bind the properties of the family tree to the view
			// * bind image using Picasso
			Picasso.get()
				.load(familyMember.getImageUri())
				.placeholder(R.drawable.user)
				.into(image)
			// * bind full name
			fullName.text = familyMember.getFullName()
			// * bind click listener
			itemView.setOnClickListener {
				itemListener.invoke(familyMember, it)
			}
		}
	}
}