package com.zjh.ktwanandroid.app.widget.openingstartanimation

import android.graphics.*
import android.graphics.drawable.Drawable
import com.zjh.ktwanandroid.app.widget.openingstartanimation.util.BitmapUtils.drawableToBitmap

/**
 * @author Jackdow
 * @version 1.0
 * FancyView
 */
class RYBDrawStrategyStateTwo : RYBDrawStrategyStateInterface {
    override fun drawIcon(canvas: Canvas, fraction: Float, drawable: Drawable, colorOfIcon: Int, widthAndHeightOfView: WidthAndHeightOfView) {
        val centerX: Int = widthAndHeightOfView.width / 2
        val centerY: Int = widthAndHeightOfView.height / 2 - 150
        canvas.save()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val newFraction = (fraction - 0.65f) / 0.35f
        paint.color = Color.parseColor("#e53935")
        canvas.drawCircle(
            centerX.toFloat(),
            (centerY - 50).toFloat(),
            100 * (1 - newFraction),
            paint
        )
        paint.color = Color.parseColor("#fdd835")
        canvas.drawCircle(
            (centerX - 35).toFloat(),
            (centerY + 35).toFloat(),
            100 * (1 - newFraction),
            paint
        )
        paint.color = Color.parseColor("#1e88e5")
        canvas.drawCircle(
            (centerX + 35).toFloat(),
            (centerY + 35).toFloat(),
            100 * (1 - newFraction),
            paint
        )
        canvas.restore()
        canvas.save()
        val path = Path()
        val bitmap: Bitmap = drawableToBitmap(drawable)
        val matrix = Matrix()
        matrix.postScale(1.7f, 1.7f, centerX.toFloat(), centerY.toFloat())
        canvas.concat(matrix)
        path.addCircle(
            centerX.toFloat(),
            centerY.toFloat(),
            bitmap.height * 1.5f * newFraction,
            Path.Direction.CW
        )
        canvas.clipPath(path)
        canvas.drawBitmap(
            bitmap,
            (centerX - bitmap.width / 2).toFloat(),
            (centerY - bitmap.height / 2).toFloat(),
            paint
        )
        canvas.restore()
    }

}