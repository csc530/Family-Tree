package com.csc530.familytree.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.csc530.familytree.R
import com.csc530.familytree.models.FamilyTree
import java.text.DateFormat
import java.util.*

/**
 * Family tree adapter for RecyclerView to display a [List] of [FamilyTree] objects
 *
 * @property context the [Context] of the application
 * @property familyTrees the [List] of [FamilyTree] objects to display
 * @property itemClickListener the [FamilyTreeClickListener] to handle item clicks
 * @property itemDeleteListener the [FamilyTreeClickListener] to handle item click of the delete button within a child view
 * @property itemEditListener the [FamilyTreeClickListener] to handle item click of the edit button within a child view
 * @constructor Create [FamilyTreeAdapter] with the given [Context], [List] of [FamilyTree] objects, and [FamilyTreeClickListener]s
 */
class FamilyTreeAdapter(
		val context: Context,
		private val familyTrees: List<FamilyTree>,
		private val itemClickListener: FamilyTreeClickListener,
		private val itemDeleteListener: FamilyTreeClickListener,
		private val itemEditListener: FamilyTreeClickListener,
) : RecyclerView.Adapter<FamilyTreeAdapter.FamilyTreeViewHolder>()
{
	/**
	 * Family tree click listener.
	 * an onClickListener for the [FamilyTreeAdapter] child view objects
	 */
	fun interface FamilyTreeClickListener
	{
		/**
		 * Called when a [FamilyTree] object is clicked or one of it's component parts is clicked
		 *
		 * @param familyTree Family tree associated with the child view
		 * @param view [View] of the child view that was clicked
		 */
		fun onItemClick(familyTree: FamilyTree, view: View)
	}
	
	inner class FamilyTreeViewHolder(view: View) : RecyclerView.ViewHolder(view)
	{
		val image: ImageView = itemView.findViewById(R.id.imgFamTreeImg)
		val title: TextView = itemView.findViewById(R.id.txtFamTreeName)
		val dateCreated: TextView = itemView.findViewById(R.id.txtCreationDate)
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
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyTreeViewHolder
	{
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_load_tree, parent, false)
		return FamilyTreeViewHolder(view)
	}
	
	/**
	 * Returns the total number of items in the data set held by the adapter.
	 *
	 * @return The total number of items in this adapter.
	 */
	override fun getItemCount(): Int
	{
		return familyTrees.size
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
	override fun onBindViewHolder(holder: FamilyTreeViewHolder, position: Int)
	{
		val familyTree = familyTrees[position]
		with(holder) {
			// ? bind the properties of the family tree to the view
			// * bind image and name properties
			image.setImageResource(familyTree.image ?: R.drawable.family_tree_load)
			title.text = familyTree.name
			// * bind date created property
			val date = DateFormat.getDateInstance(DateFormat.FULL).format(familyTree.created.toDate())
			dateCreated.text = context.getString(R.string.date_created_placeholder, date)
			// * set the on click listeners for the view
			itemView.setOnClickListener {
				itemClickListener.onItemClick(familyTree, it)
			}
			itemView.findViewById<ImageButton>(R.id.imgbtnDelete).setOnClickListener {
				itemDeleteListener.onItemClick(familyTree, it)
			}
			itemView.findViewById<ImageButton>(R.id.imgbtnEdit).setOnClickListener {
				itemEditListener.onItemClick(familyTree, it)
			}
		}
	}
	
}