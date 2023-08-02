package com.example.abschlussaufgabe.data.datamodels.modelForFight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class ChakraPointsBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var maxChakraPoints = 500
    private var currentChakraPoints = 500

    private val backgroundPaint = Paint().apply {
        color = Color.GRAY
    }

    private val chakraPointsPaint = Paint().apply {
        color = Color.rgb(255, 105, 0)
    }

    fun setMaxChakraPoints(maxChakraPoints: Int) {
        this.maxChakraPoints = maxChakraPoints
        invalidate()
    }

    fun setCurrentChakraPoints(currentChakraPoints: Int) {
        this.currentChakraPoints = currentChakraPoints
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            // Zeichne den Hintergrund
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

            // Berechne die Breite des farbigen Balkens basierend auf dem Verhältnis der aktuellen Lebenspunkte zu den maximalen Lebenspunkten
            val chakraPointsRatio = currentChakraPoints.toFloat() / maxChakraPoints
            val coloredBarWidth = width * chakraPointsRatio

            // Zeichne den farbigen Balken
            canvas.drawRect(0f, 0f, coloredBarWidth, height.toFloat(), chakraPointsPaint)
        }
    }
}