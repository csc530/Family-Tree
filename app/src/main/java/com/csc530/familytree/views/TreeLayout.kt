package com.csc530.familytree.views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.csc530.familytree.R
import kotlin.math.max
import kotlin.streams.toList

/**
 * TODO: document your custom view class.
 */
class TreeLayout : ViewGroup
{
	
	
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
		a.recycle()
	}
	
	
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)
		val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
		val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
		val resizeWidth = widthSpecMode != MeasureSpec.EXACTLY
		val resizeHeight = heightSpecMode != MeasureSpec.EXACTLY
		val measureSpecWidth = MeasureSpec.getSize(widthMeasureSpec)
		val measureSpecHeight = MeasureSpec.getSize(heightMeasureSpec)
		var usedWidth = max(paddingLeft, paddingStart) + max(paddingRight, paddingEnd)
		var usedHeight = paddingTop + paddingBottom
		for(child in children)
		{
			val childHeightSpecMode =
					if(layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT && measureSpecHeight != 0)
						MeasureSpec.AT_MOST
					else if(heightSpecMode == MeasureSpec.EXACTLY && measureSpecHeight == 0)
						MeasureSpec.UNSPECIFIED
					else
						heightSpecMode
			val childWidthSpecMode =
					if(layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT && measureSpecWidth != 0)
						MeasureSpec.AT_MOST
					else if(widthSpecMode == MeasureSpec.EXACTLY && measureSpecWidth == 0)
						MeasureSpec.UNSPECIFIED
					else
						widthSpecMode
			val childWidthSpec = MeasureSpec.makeMeasureSpec(measureSpecWidth, childWidthSpecMode)
			val childHeightSpec = MeasureSpec.makeMeasureSpec(measureSpecHeight, childHeightSpecMode)
			child.measure(childWidthSpec, childHeightSpec)
			usedWidth += child.measuredWidth
			usedHeight += child.measuredHeight
		}
		if(!resizeWidth)
			usedWidth = measureSpecWidth
		if(!resizeHeight)
			usedHeight = measureSpecHeight
		setMeasuredDimension(resolveSize(usedWidth, widthMeasureSpec), resolveSize(usedHeight, heightMeasureSpec))
		println("Resizable: width: $resizeWidth, height: $resizeHeight")
		println("Used width: $usedWidth, Used Height: $usedHeight")
		println("Parent; width: $measureSpecWidth, height: $measureSpecHeight")
		println("OnMeasure(); Width: $width, Height: $height")
	}
	
	private var children: ArrayList<View> = ArrayList<View>()
	override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int)
	{
<<<<<<< HEAD
=======
		//TODO: currently working on laying out children in relation to layout parameter of father mother
		//and spacing with provided child information
>>>>>>> 9946868 (:construction: perf(views): Family Tree)
		if(!changed) return
		for(i in 0 until this.childCount)
		{
			val child: View = getChildAt(i)
			if(child.visibility != GONE && child.layoutParams is LayoutParams)
				children.add(child)
		}
		val rootChildren = children.stream().filter {
			val lp = it.layoutParams as LayoutParams
			lp.father != lp.NO_PARENT || lp.mother != lp.NO_PARENT
		}.toList()
		for(child in rootChildren)
		{
			for(c in children)
					if((c.layoutParams as LayoutParams).father == child.id)
				(child.layoutParams as LayoutParams).children++
			val layoutParams = child.layoutParams
			if(layoutParams !is LayoutParams)
				continue
			val childLeft: Int = paddingLeft + layoutParams.width
			val childTop: Int = paddingTop + layoutParams.height
			child.layout(childLeft, childTop,
			             childLeft + child.measuredWidth,
			             childTop + child.measuredHeight)
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
	inner class LayoutParams : ViewGroup.LayoutParams
	{
		val NO_PARENT = -1
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