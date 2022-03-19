package com.csc530.familytree.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.csc530.familytree.R
import kotlin.math.max


/**
 * TODO: document your custom view class.
 */
class TreeView : ViewGroup
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
		val a = context.obtainStyledAttributes(
				attrs, R.styleable.TreeView, defStyle, 0)
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
					if(layoutParams.height == LayoutParams.MATCH_PARENT)
						MeasureSpec.AT_MOST
					else
						heightSpecMode
			val childWidthSpecMode =
					if(layoutParams.width == LayoutParams.MATCH_PARENT)
						MeasureSpec.AT_MOST
					else
						widthSpecMode
			val childWMeasureSpec = MeasureSpec.makeMeasureSpec(measureSpecWidth, childWidthSpecMode)
			val childHMeasureSpec = MeasureSpec.makeMeasureSpec(measureSpecHeight, childHeightSpecMode)
			child.measure(childWMeasureSpec, childHMeasureSpec)
			usedWidth += child.width
			usedHeight += child.height
		}
		//		if(!resizeWidth)
		usedWidth = measureSpecWidth
		//		if(!resizeHeight)
		usedHeight = measureSpecHeight
		setMeasuredDimension(resolveSize(usedWidth, widthMeasureSpec), resolveSize(usedHeight, heightMeasureSpec))
	}
	
	override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int)
	{
		if(!changed) return
		
		for(i in 0 until this.childCount)
		{
			val child: View = getChildAt(i)
			if(child.visibility != GONE)
			{
				val lp = child.layoutParams
				val childLeft: Int = paddingLeft + lp.width
				val childTop: Int = paddingTop + lp.height
				child.layout(childLeft, childTop,
				             childLeft + child.measuredWidth,
				             childTop + child.measuredHeight)
			}
		}
	}
	
	override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams
	{
		return LayoutParams(context, attrs)
	}
	
	override fun generateDefaultLayoutParams(): LayoutParams
	{
		return LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
	}
	
	override fun generateLayoutParams(p: LayoutParams?): LayoutParams
	{
		return LayoutParams(p)
	}
	
	override fun checkLayoutParams(p: LayoutParams?): Boolean
	{
		return p is LayoutParams
	}
	
	override fun shouldDelayChildPressedState(): Boolean
	{
		return false
	}
}