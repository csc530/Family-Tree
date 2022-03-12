package com.csc530.familytree.views

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.csc530.familytree.R

class FamilyMemberView : View
{
	
	private val DEFAULT_FONT_SIZE: Float = 35f
	private val DEFAULT_FONT_COLOUR: Int = Color.BLACK
	private val DEFAULT_PORTRAIT = R.drawable.user
	
	private var portrait: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.user)
		set(value)
		{
			field = value
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
			postInvalidate()
		}
	var lastName: String = "Box"
		set(value)
		{
			field = value
			textWidth = textPaint.measureText("$firstName $field")
			textHeight = textPaint.fontMetrics.bottom
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
		val a = context.obtainStyledAttributes(
				attributeSet, R.styleable.MemberView, defStyle, 0)
		
		firstName = a.getString(
				R.styleable.MemberView_firstName).toString()
		fontColour = a.getColor(
				R.styleable.MemberView_fontColour,
				DEFAULT_FONT_COLOUR)
		// Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
		// values that should fall on pixel boundaries.
		fontSize = a.getDimension(
				R.styleable.MemberView_fontSize,
				DEFAULT_FONT_SIZE)
		
		if(a.hasValue(R.styleable.MemberView_portrait))
		{
			val drawable = a.getResourceId(R.styleable.MemberView_portrait, DEFAULT_PORTRAIT)
			portrait = BitmapFactory.decodeResource(resources, drawable)
			/*? This is to set the portrait size to be within the view's bounds (height&width)
			? Necessary because within the init it hasn't drawn (know) it's dimensions*/
			viewTreeObserver.addOnGlobalLayoutListener(
					// ? Resizes the image whenever the container's width or height changes to remain within it
					object : OnGlobalLayoutListener
					{
						override fun onGlobalLayout()
						{
							if(width < portrait.width || height < portrait.height) return
							viewTreeObserver.removeOnGlobalLayoutListener(this)
							portrait = resizeBitmap(portrait, width.toFloat(), height.toFloat())
						}
					})
		}
		a.recycle()
		
		// Update TextPaint and text measurements from attributes
		postInvalidate()
	}
	
	private fun resizeBitmap(bitmap: Bitmap, width: Float, height: Float): Bitmap
	{
		//? Thank you to Coderz Geek - https://youtu.be/BxBcs1ddEn8?t=350
		val matrix = Matrix()
		val src = RectF(0F, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
		val dst = RectF(0f, 0f, width, height)
		println(matrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER))
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
	}
	
	override fun onDraw(canvas: Canvas?)
	{
		super.onDraw(canvas)
		
		//? set the width that content can take up
		val contentWidth: Float = (width - paddingLeft - paddingRight).toFloat()
		val contentHeight: Float = (height - paddingTop - paddingBottom).toFloat()
		
		updateTextPaint()
		portrait = resizeBitmap(portrait, contentWidth, contentHeight / 2f)
		if(portrait.width.toFloat() != contentWidth)
		{
			val centerX: Float = (paddingLeft + contentWidth / 2f) - portrait.width / 2f
			canvas?.drawBitmap(portrait, centerX, paddingTop.toFloat(), null)
		}
		else
			canvas?.drawBitmap(portrait, paddingLeft.toFloat(), paddingTop.toFloat(), null)
		// Draw the text.
		val name = "$firstName $lastName"
		canvas?.drawText(name, (paddingLeft + contentWidth / 2) - textWidth / 2f,
						  // ? place text below the image
				         paddingTop + (contentHeight + portrait.height + textHeight) / 2,
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