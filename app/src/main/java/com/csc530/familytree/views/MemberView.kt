package com.csc530.familytree.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.csc530.familytree.R

/**
 * TODO: document your custom view class.
 */
class MemberView : LinearLayout {
	
	private lateinit var textPaint: TextPaint
	private var textWidth: Float = 0f
	private var textHeight: Float = 0f
	
	/**
	 * The text to draw
	 */
	var firstName: String? = "Talon"
		set(value) {
			field = value
			invalidateTextPaintAndMeasurements()
		}
	
	/**
	 * The font color
	 */
	var fontColour: Int = R.color.colorPrimary
		set(value) {
			field = value
			invalidateTextPaintAndMeasurements()
		}
	
	/**
	 * In the example view, this dimension is the font size.
	 */
	var fontSize: Float = 25f
		set(value) {
			field = value
			invalidateTextPaintAndMeasurements()
		}
	
	/**
	 * In the example view, this drawable is drawn above the text.
	 */
	var exampleDrawable: Drawable? = null
	
	constructor(context: Context) : super(context) {
		init(null, 0)
	}
	
	constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
		init(attrs, 0)
	}
	
	constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
		init(attrs, defStyle)
	}
	
	private fun init(attrs: AttributeSet?, defStyle: Int) {
		// Load attributes
		val a = context.obtainStyledAttributes(
			attrs, R.styleable.MemberView, defStyle, 0)
		
		firstName = a.getString(
			R.styleable.MemberView_exampleString)
		fontColour = a.getColor(
			R.styleable.MemberView_exampleColor,
			fontColour)
		// Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
		// values that should fall on pixel boundaries.
		fontSize = a.getDimension(
			R.styleable.MemberView_exampleDimension,
			fontSize)
		
		if(a.hasValue(R.styleable.MemberView_exampleDrawable)) {
			exampleDrawable = a.getDrawable(
				R.styleable.MemberView_exampleDrawable)
			exampleDrawable?.callback = this
		}
		
		a.recycle()
		
		// Set up a default TextPaint object
		textPaint = TextPaint().apply {
			flags = Paint.ANTI_ALIAS_FLAG
			textAlign = Paint.Align.LEFT
		}
		
		// Update TextPaint and text measurements from attributes
		invalidateTextPaintAndMeasurements()
	}
	
	private fun invalidateTextPaintAndMeasurements() {
		textPaint.let {
			it.textSize = fontSize
			it.color = fontColour
			textWidth = it.measureText(firstName)
			textHeight = it.fontMetrics.bottom
		}
		//redraw the view, assign that values are old so redo them basically
		invalidate()
	}
	
	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		
		// TODO: consider storing these as member variables to reduce
		// allocations per draw cycle.
		val paddingLeft = paddingLeft
		val paddingTop = paddingTop
		val paddingRight = paddingRight
		val paddingBottom = paddingBottom
		
		val contentWidth = width - paddingLeft - paddingRight
		val contentHeight = height - paddingTop - paddingBottom
		
		firstName?.let {
			// Draw the text.
			canvas.drawText(it,
			                paddingLeft + (contentWidth - textWidth) / 2,
			                paddingTop + (contentHeight + textHeight) / 2,
			                textPaint)
		}
		
		// Draw the example drawable on top of the text.
		exampleDrawable?.let {
			it.setBounds(paddingLeft, paddingTop,
			             paddingLeft + contentWidth, paddingTop + contentHeight)
			it.draw(canvas)
		}
	}
	
	override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
		TODO("Not yet implemented")
	}
}