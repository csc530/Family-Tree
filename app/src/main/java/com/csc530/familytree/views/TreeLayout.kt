package com.csc530.familytree.views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.csc530.familytree.R
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.streams.toList

/**
 * TODO: document your custom view class.
 */
class TreeLayout : ViewGroup
{
	companion object Constants
	{
		// * for child sizing
		const val LARGEST_CHILD = 1
		const val SMALLEST_CHILD = 2
		const val AVERAGE_CHILDREN = 0
		const val MEDIAN_CHILD = 3
		const val CUSTOM_SIZE = 4
		
		/* * for layout direction*/
		
		const val TOP_TO_BOTTOM = 0
		const val BOTTOM_TO_TOP = 1
		const val LEFT_TO_RIGHT = 2
		const val RIGHT_TO_LEFT = 3
	}
	
	//TODO: make enums https://www.baeldung.com/kotlin/enum
	var treeDirection = TOP_TO_BOTTOM
	var equalChildSizes: Boolean = true
	var childSizes: Int = AVERAGE_CHILDREN
		set(value)
		{
			field = value
			postInvalidate()
		}
	var customChildSize: Int = -1
		set(value)
		{
			field = value
			if(childSizes == customChildSize)
				postInvalidate()
		}
	var siblingPadding: Int = 15
		set(value)
		{
			field = value
			postInvalidate()
		}
	var parentalPadding: Int = 15
		set(value)
		{
			field = value
			postInvalidate()
		}
	
	constructor(context: Context) : super(context)
	{
		init(null, 0)
	}
	
	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
	{
		init(attrs, 0)
	}
	
	constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)
	{
		init(attrs, defStyle)
	}
	
	private fun init(attrs: AttributeSet?, defStyle: Int)
	{
		// Load attributes
		val a = context.obtainStyledAttributes(attrs, R.styleable.TreeLayout, defStyle, 0)
		childSizes = a.getInt(R.styleable.TreeLayout_child_size, AVERAGE_CHILDREN)
		equalChildSizes = a.getBoolean(R.styleable.TreeLayout_equal_child_size, true)
		treeDirection = a.getInt(R.styleable.TreeLayout_direction, TOP_TO_BOTTOM)
		customChildSize = a.getInt(R.styleable.TreeLayout_custom_child_size, -1)
		siblingPadding = a.getInt(R.styleable.TreeLayout_sibling_padding, 15)
		parentalPadding = a.getInt(R.styleable.TreeLayout_parental_padding, 15)
		a.recycle()
	}
	
	private val childHeights = HashMap<View, Int>()
	private val childWidths = HashMap<View, Int>()
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
	{
		if(childCount == 0)
		{
			super.onMeasure(widthMeasureSpec, heightMeasureSpec)
			return
		}
		//clear the widths and heights from previous measure of children
		childWidths.clear()
		childHeights.clear()
		val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
		val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
		val resizeWidth = widthSpecMode != MeasureSpec.EXACTLY
		val resizeHeight = heightSpecMode != MeasureSpec.EXACTLY
		val measureSpecWidth = MeasureSpec.getSize(widthMeasureSpec)
		val measureSpecHeight = MeasureSpec.getSize(heightMeasureSpec)
		var maxWidth = max(paddingLeft, paddingStart) + max(paddingRight, paddingEnd) + siblingPadding * (childCount)
		var maxHeight = paddingTop + paddingBottom + parentalPadding * (childCount)
		
		for(i in 0 until childCount)
		{
			val child = getChildAt(i)
			val childWidthSpec = MeasureSpec.makeMeasureSpec(measureSpecWidth, widthSpecMode)
			val childHeightSpec = MeasureSpec.makeMeasureSpec(measureSpecHeight, heightSpecMode)
			child.measure(childWidthSpec, childHeightSpec)
			childWidths[child] = child.measuredWidth
			childHeights.put(child, child.measuredHeight)
		}
		var childWidth = 0
		var childHeight = 0
		when(childSizes)
		{
			LARGEST_CHILD    ->
			{
				childHeight = childHeights.values.maxOf { it }
				childWidth = childWidths.values.maxOf { it }
			}
			SMALLEST_CHILD   ->
			{
				childHeight = childHeights.values.minOf { it }
				childWidth = childWidths.values.minOf { it }
			}
			AVERAGE_CHILDREN ->
			{
				// if prevent Nan comparisons of empty layouts causing errors
				if(childHeights.size > 0)
					childHeight = childHeights.values.average().roundToInt()
				if(childWidths.size > 0)
					childWidth = childWidths.values.average().roundToInt()
			}
			MEDIAN_CHILD     ->
			{
				childHeight = childHeights.values.stream().sorted().toList()[childHeights.size / 2]
				childWidth = childWidths.values.stream().sorted().toList()[childHeights.size / 2]
			}
			CUSTOM_SIZE      ->
			{
				childHeight = customChildSize
				childWidth = customChildSize
			}
			else             -> //!! an error has occurred if it's not one of the selected enums but lay them out how they want
				//!! TODO: important to make enum so it can't be assigned a random int
			{
				setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec), resolveSize(maxHeight, heightMeasureSpec))
				return
			}
		}
		
		maxWidth += childWidth * childCount
		maxHeight += childHeight * childCount
		
		if(!resizeWidth)
			maxWidth = measureSpecWidth
		if(!resizeHeight)
			maxHeight = measureSpecHeight
		//check width and height are not greater than parent's border's with padding
		
		if(childWidth * childCount > maxWidth)
			childWidth -= (childWidth * childCount - maxWidth) / childCount
		if(childHeight * childCount > maxHeight)
			childHeight -= (childHeight * childCount - maxHeight) / childCount
		//ensure that height and width are at least 1
		childHeight.coerceAtLeast(1)
		childWidth.coerceAtLeast(1)
		//update measurements of children to be within the new calculated amount
		val childHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST)
		val childWidthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST)
		for(i in 0 until childCount)
		{
			val child = getChildAt(i)
			child.measure(childWidthSpec, childHeightSpec)
			childWidths[child] = child.measuredWidth
			childHeights.put(child, child.measuredHeight)
		}
		
		//		println(MeasureSpec.toString(widthMeasureSpec))
		//		println(MeasureSpec.toString(heightMeasureSpec))
		setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec), resolveSize(maxHeight, heightMeasureSpec))
		//				println("Resizable: width: $resizeWidth, height: $resizeHeight")
		//				println("Used width: $maxWidth, Used Height: $maxHeight")
		//				println("Parent; width: $measureSpecWidth, height: $measureSpecHeight")
		//				println("OnMeasure(); Width: $width, Height: $height")
	}
	
	/** To be used to store all children during layout*/
	private val layoutChildren = ArrayList<View>()
	override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int)
	{
		if(!changed) return
		laidOutChildren.clear()
		// ? Lay children with no parents in a row
		for(i in 0 until childCount)
			layoutChildren.add(getChildAt(i))
		
		var siblingSpacing = paddingLeft
		layoutChildren.stream()
			.filter { parent ->
				if(parent.layoutParams !is LayoutParams || parent.visibility == GONE)
					return@filter false
				val childParams = parent.layoutParams as LayoutParams
				childParams.mother == LayoutParams.NO_PARENT && childParams.father == LayoutParams.NO_PARENT
			}
			.forEach { parent ->				println(parent.getResources().getResourceName(parent.getId()))
				
				val childLeft: Int = siblingSpacing
				val childTop: Int = paddingTop
				parent.layout(childLeft, childTop,
				              childLeft + childWidths.get(parent)!!,
				              childTop + parent.measuredHeight)
				laidOutChildren.add(parent)
				siblingSpacing += siblingPadding + childWidths[parent]!!
				//				println("${parent.id}\n\t${parent.measuredWidth} =? ${childWidths[parent]}")
				layoutChildren(parent, parentalPadding + paddingTop+ childHeights[parent]!!)
			}
		
		//			val layoutParams = child.layoutParams
		//			if(child.visibility != GONE && child.layoutParams is LayoutParams)
		//			{
		//					val childLeft: Int = paddingLeft
		//					val childTop: Int = paddingTop
		//				println()
		//				println("width: ${layoutParams.width}, height: ${layoutParams.height}")
		//					child.layout(childLeft, childTop,
		//					             childLeft + child.measuredWidth,
		//					             childTop + child.measuredHeight)
		//			}
		//		}
		//		for(i in 0 until this.childCount)
		//		{
		//			val child: View = getChildAt(i)
		//			if(child.visibility != GONE && child.layoutParams is LayoutParams)
		//				children.add(child)
		//		}
		//		val rootChildren = children.stream().filter {
		//			val lp = it.layoutParams as LayoutParams
		//			lp.father != lp.NO_PARENT || lp.mother != lp.NO_PARENT
		//		}.toList()
		//		for(child in rootChildren)
		//		{
		//			for(c in children)
		//					if((c.layoutParams as LayoutParams).father == child.id)
		//				(child.layoutParams as LayoutParams).children++
		//			val layoutParams = child.layoutParams
		//			if(layoutParams !is LayoutParams)
		//				continue
		//			val childLeft: Int = paddingLeft + layoutParams.width
		//			val childTop: Int = paddingTop + layoutParams.height
		//			child.layout(childLeft, childTop,
		//			             childLeft + child.measuredWidth,
		//			             childTop + child.measuredHeight)
		//		}
	}
	
	private var laidOutChildren = ArrayList<View>()
	private fun layoutChildren(parent: View, parentalSpacing: Int)
	{
		var siblingSpacing = paddingLeft
		layoutChildren.stream()
			.filter {
				if(it.layoutParams !is LayoutParams || laidOutChildren.contains(it))
					return@filter false
				val childParams = it.layoutParams as LayoutParams
				childParams.mother == parent.id || childParams.father == parent.id
			}
			.forEach {child->
				val left: Int = paddingLeft + siblingSpacing
				val top: Int = paddingTop  + parentalSpacing
				parent.layout(left, top,
				              left + childWidths[child]!!,
				              top + childHeights[child]!!)
				laidOutChildren.add(child)
				layoutChildren(child, parentalSpacing + parentalPadding)
				siblingSpacing += siblingPadding
			}
	}
	
	override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean
	{
		return p is LayoutParams
	}
	
	override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams
	{
		return LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
	}
	
	override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams
	{
		if(attrs == null)
			return LayoutParams(context, attrs)
		return LayoutParams(context, attrs)
	}
	
	override fun generateLayoutParams(p: ViewGroup.LayoutParams?): ViewGroup.LayoutParams
	{
		return LayoutParams(p)
	}
	
	/**
	 * Layout params for TreeLayout ViewGroup
	 *
	 * @constructor Create empty constructor for layout params
	 */
	class LayoutParams : ViewGroup.LayoutParams
	{
		companion object Constants
		{
			const val NO_PARENT: Int = -1
		}
		
		var father: Int = NO_PARENT
		var mother: Int = NO_PARENT
		var level: Int = 1
		var children: Int = 0
		
		constructor(layoutParams: ViewGroup.LayoutParams) : super(layoutParams)
		{
			this.width = layoutParams.width
			this.height = layoutParams.height
		}
		
		constructor(width: Int, height: Int) : super(width, height)
		
		constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
		{
			val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TreeLayout_Layout)
			father = a.getResourceId(R.styleable.TreeLayout_Layout_father, -1)
			mother = a.getResourceId(R.styleable.TreeLayout_Layout_mother, -1)
			level = a.getInt(R.styleable.TreeLayout_Layout_level, 1)
			children = a.getInt(R.styleable.TreeLayout_Layout_children, 0)
			a.recycle()
		}
	}
}