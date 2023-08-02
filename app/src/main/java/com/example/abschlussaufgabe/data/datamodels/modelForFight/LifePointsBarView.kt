package com.example.abschlussaufgabe.data.datamodels.modelForFight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class LifePointsBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var maxLifePoints = 500
    private var currentLifePoints = 500

    private val backgroundPaint = Paint().apply {
        color = Color.GRAY
    }

    private val lifePointsPaint = Paint().apply {
        color = Color.GREEN
    }

    fun setMaxLifePoints(maxLifePoints: Int) {
        this.maxLifePoints = maxLifePoints
        invalidate()
    }

    fun setCurrentLifePoints(currentLifePoints: Int) {
        this.currentLifePoints = currentLifePoints
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            // Zeichne den Hintergrund
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

            // Berechne die Breite des farbigen Balkens basierend auf dem Verhältnis der aktuellen Lebenspunkte zu den maximalen Lebenspunkten
            val lifePointsRatio = currentLifePoints.toFloat() / maxLifePoints
            val coloredBarWidth = width * lifePointsRatio

            // Zeichne den farbigen Balken
            canvas.drawRect(0f, 0f, coloredBarWidth, height.toFloat(), lifePointsPaint)
        }
    }
}
