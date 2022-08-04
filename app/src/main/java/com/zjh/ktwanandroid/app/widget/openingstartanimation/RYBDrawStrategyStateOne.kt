package com.zjh.ktwanandroid.app.widget.openingstartanimation
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable

/**
 * @author Jackdow
 * @version 1.0
 * FancyView
 */
class RYBDrawStrategyStateOne : RYBDrawStrategyStateInterface {
    override fun drawIcon(canvas: Canvas, fraction: Float, drawable: Drawable, colorOfIcon: Int, widthAndHeightOfView: WidthAndHeightOfView) {
        val newFraction = fraction / 0.65f
        val centerX: Int = widthAndHeightOfView.width / 2
        val centerY: Int = widthAndHeightOfView.height / 2 - 150
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.save()
        paint.color = Color.parseColor("#e53935")
        if (newFraction <= 0.33f) {
            canvas.drawCircle(
                centerX.toFloat(),
                (centerY - 50).toFloat(),
                100 * (newFraction / 0.33f),
                paint
            )
        } else {
            canvas.drawCircle(centerX.toFloat(), (centerY - 50).toFloat(), 100f, paint)
        }
        if (newFraction > 0.33f) {
            paint.color = Color.parseColor("#fdd835")
            if (newFraction <= 0.66f) canvas.drawCircle(
                (centerX - 35).toFloat(),
                (centerY + 35).toFloat(),
                100 * ((newFraction - 0.33f) / 0.33f),
                paint
            ) else canvas.drawCircle(
                (centerX - 35).toFloat(),
                (centerY + 35).toFloat(),
                100f,
                paint
            )
        }
        if (newFraction > 0.66f) {
            paint.color = Color.parseColor("#1e88e5")
            if (newFraction <= 1f) canvas.drawCircle(
                (centerX + 35).toFloat(),
                (centerY + 35).toFloat(),
                100 * ((newFraction - 0.66f) / 0.34f),
                paint
            ) else canvas.drawCircle(
                (centerX + 35).toFloat(),
                (centerY + 35).toFloat(),
                100f,
                paint
            )
        }
        canvas.restore()
    }
}