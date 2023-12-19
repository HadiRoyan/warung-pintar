package com.capstone.warungpintar.ui.addproduct

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class BorderOverlay @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private val borderPaint: Paint
    private val guidelinesPaint: Paint

    init {
        borderPaint = Paint()
        borderPaint.color = Color.YELLOW
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = 5f
        guidelinesPaint = Paint()
        guidelinesPaint.style = Paint.Style.STROKE
        guidelinesPaint.strokeWidth = 2f
        guidelinesPaint.alpha = 128 // Set alpha to make guidelines semi-transparent
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Get the center of the view
        val centerX = canvas.width / 2f
        val centerY = canvas.height / 2f

        // Define the border rectangle
        val borderLeft = centerX - 350 // Adjust as needed
        val borderTop = centerY - 200 // Adjust as needed
        val borderRight = centerX + 350 // Adjust as needed
        val borderBottom = centerY + 200 // Adjust as needed

        // Draw the border
        canvas.drawRect(borderLeft, borderTop, borderRight, borderBottom, borderPaint)

        // Draw guidelines (you can customize these as needed)
        val guideline1X = centerX - 250
        val guideline2X = centerX + 250
        val guideline1Y = centerY - 150
        val guideline2Y = centerY + 150
        //canvas.drawLine(guideline1X, centerY, guideline2X, centerY, guidelinesPaint)
        //canvas.drawLine(centerX, guideline1Y, centerX, guideline2Y, guidelinesPaint)
    }
}