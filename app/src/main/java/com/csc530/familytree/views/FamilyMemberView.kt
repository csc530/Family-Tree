package com.csc530.familytree.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.csc530.familytree.R
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class FamilyMemberView : View
{
	
	private val DEFAULT_FONT_SIZE: Float = 35f
	private val DEFAULT_FONT_COLOUR: Int = Color.BLACK
	
	private lateinit var portraitFrame: ImageView
	private var portrait: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.user, context.theme)
		set(value)
		{
			field = value
			requestLayout()
			postInvalidate()
		}
	
	/**
	 * The text to draw; member's first name
	 */
	var firstName: String = "Talon"
		set(value)
		{
			field = value
			textWidth = textPaint.measureText("$field $lastName")
			textHeight = textPaint.fontMetrics.bottom
			requestLayout()
			postInvalidate()
		}
	var lastName: String = "Box"
		set(value)
		{
			field = value
			textWidth = textPaint.measureText("$firstName $field")
			textHeight = textPaint.fontMetrics.bottom
			requestLayout()
			postInvalidate()
		}
	
	// Set up a default TextPaint object
	private var textPaint: TextPaint = TextPaint().apply {
		flags = Paint.ANTI_ALIAS_FLAG
		textAlign = Paint.Align.LEFT
	}
	private var textWidth: Float = textPaint.measureText(firstName)
	private var textHeight: Float = textPaint.fontMetrics.bottom
	
	
	/**
	 * The font color of all text
	 */
	var fontColour: Int = R.color.black
		//TODO: change to colour on primary
		set(value)
		{
			field = value
			textPaint.color = field
			postInvalidate()
		}
	
	/**
	 * In the example view, this dimension is the font size.
	 */
	var fontSize: Float = 25f
		set(value)
		{
			field = value
			textPaint.textSize = field
			requestLayout()
			postInvalidate()
		}
	
	
	// * BELOW - necessary parent constructors form View class
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
	
	private fun init(attributeSet: AttributeSet?, defStyle: Int)
	{
		// Load attributes//TODO change view class name to FamilyMemberView same as declarableStyle name
		val a = context.obtainStyledAttributes(attributeSet, R.styleable.FamilyMemberView, defStyle, 0)
		
		firstName = a.getString(R.styleable.FamilyMemberView_firstName) ?: firstName
		fontColour = a.getColor(R.styleable.FamilyMemberView_fontColour, DEFAULT_FONT_COLOUR)
		// Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
		// values that should fall on pixel boundaries.
		fontSize = a.getDimension(R.styleable.FamilyMemberView_fontSize, DEFAULT_FONT_SIZE)
		
		portrait = a.getDrawable(R.styleable.FamilyMemberView_portrait)
		           ?: ResourcesCompat.getDrawable(resources, R.drawable.user, context.theme)
		portraitFrame = ImageView(context, attributeSet, defStyle)
		portraitFrame.setImageDrawable(portrait)
		portraitFrame.scaleType = ImageView.ScaleType.CENTER_INSIDE
		a.recycle()
		// Update TextPaint and text measurements from attributes
		//
		//		postInvalidate()
	}
	
	
	private var viewHeight: Int = 0
	private var viewWidth: Int = 0
	
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)
		val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
		val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
		val resizeWidth = widthSpecMode != MeasureSpec.EXACTLY
		val resizeHeight = heightSpecMode != MeasureSpec.EXACTLY
		val measureSpecWidth = MeasureSpec.getSize(widthMeasureSpec)
		val measureSpecHeight = MeasureSpec.getSize(heightMeasureSpec)
		//updates the measurements oif each item in hte view
		portraitFrame.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(measureSpecHeight / 2, heightSpecMode))
		textWidth = textPaint.measureText("$firstName $lastName")
		textHeight = textPaint.fontMetrics.bottom + -textPaint.fontMetrics.top
		//calculate the size of the view if it's resizeable else use given size from parent
		viewHeight =
				if(resizeHeight)
					(paddingTop + portraitFrame.height + textHeight + paddingBottom).roundToInt()
				else
					measureSpecHeight
		viewWidth =
				if(resizeWidth)
					(max(paddingLeft, paddingStart) + portraitFrame.width + textWidth + max(paddingEnd, paddingRight)).roundToInt()
				else
					measureSpecWidth
		//set the dimension of the view
		val measuredWidth = resolveSizeAndState(viewWidth, measureSpecWidth,measuredState)
		val measuredHeight = resolveSizeAndState(viewHeight, measureSpecHeight,measuredState)
		setMeasuredDimension(measuredWidth, measuredHeight)
	}
	
	private var contentWidth: Float = (width - paddingLeft - paddingRight).toFloat()
	private var contentHeight: Float = (height - paddingTop - paddingBottom).toFloat()
	override fun onDraw(canvas: Canvas)
	{
		super.onDraw(canvas)
		
		//? set the width that content can take up
		contentWidth = (width - paddingLeft - paddingRight).toFloat()
		contentHeight = (height - paddingTop - paddingBottom).toFloat()
		
		updateTextPaint()
		portraitFrame.layout(paddingLeft, paddingTop, contentWidth.toInt(), (contentHeight / 2).toInt())
		if(portraitFrame.isLaidOut)
			portraitFrame.draw(canvas)
		else
			portrait?.let {
				it.setBounds(paddingLeft, paddingTop, paddingLeft + contentWidth.toInt(), contentHeight.toInt() / 2)
				it.draw(canvas)
			}
		
		// Draw the text.
		val name = "$firstName $lastName"
		canvas.drawText(name, (paddingLeft + contentWidth / 2) - textWidth / 2f,
				//       ? place text below the image
				        paddingTop + (contentHeight + portraitFrame.height + textHeight) / 2,
				        textPaint)
		
	}
	
	private fun updateTextPaint()
	{
		textPaint.let {
			it.textSize = fontSize
			it.color = fontColour
			//TODO: explore breakText to see if you can wrap text
			textWidth = it.measureText("$firstName $lastName")
			textHeight = it.fontMetrics.bottom
		}
	}
}