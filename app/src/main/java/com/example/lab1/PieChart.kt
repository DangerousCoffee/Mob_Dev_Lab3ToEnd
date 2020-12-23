package com.example.lab1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat


class PieChart(context: Context, attrs: AttributeSet): View(context, attrs) {

    init{
        setWillNotDraw(false)
    }

    private val radius = 400F

    private val orange = ContextCompat.getColor(context, R.color.Orange)
    private val green = ContextCompat.getColor(context, R.color.Green)
    private val black = ContextCompat.getColor(context, R.color.Black)

    private val piePaintOrange = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = orange
    }
    private val piePaintGreen = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = green
    }
    private val piePaintBlack = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = black
    }

    private val oval: RectF = RectF(550F - radius, 550F - radius, 550F + radius, 550F + radius)



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {
            drawArc(oval, 0F, 108F, true, piePaintOrange)
            drawArc(oval, 108F, 108F, true, piePaintGreen)
            drawArc(oval, 216F, 360F - 216F, true, piePaintBlack)
        }
    }
}