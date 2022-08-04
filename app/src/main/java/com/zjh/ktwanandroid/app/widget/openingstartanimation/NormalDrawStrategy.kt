package com.zjh.ktwanandroid.app.widget.openingstartanimation

import android.graphics.*
import android.graphics.drawable.Drawable
import com.zjh.ktwanandroid.app.widget.openingstartanimation.util.BitmapUtils.drawableToBitmap

/**
 * @author Jackdow
 * @version 1.0
 * FancyView
 */
class NormalDrawStrategy : DrawStrategy {
    override fun drawAppName(
        canvas: Canvas, fraction: Float, name: String, colorOfAppName: Int,
        widthAndHeightOfView: WidthAndHeightOfView?
    ) {
        canvas.save()
        val width: Int = widthAndHeightOfView?.width ?: 0
        val height: Int = widthAndHeightOfView?.height ?: 0
        val paint = Paint()
        paint.color = colorOfAppName
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 50f
        canvas.drawText(name, (width / 2).toFloat(), (height / 2 + 50).toFloat(), paint)
        canvas.restore()
    }

    override fun drawAppIcon(
        canvas: Canvas, fraction: Float, icon: Drawable?, colorOfIcon: Int,
        widthAndHeightOfView: WidthAndHeightOfView?
    ) {
        canvas.save()
        val width: Int = widthAndHeightOfView?.width ?: 0
        val height: Int = widthAndHeightOfView?.height ?: 0
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val bitmap = drawableToBitmap(icon!!)
        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height
        val radius = bitmapWidth * 3 / 2
        val centerX = width / 2 + bitmapWidth / 2
        val centerY = height / 2 - 100
        if (fraction <= 0.60) {
            val path = Path()
            val matrix = Matrix()
            matrix.postScale(
                1.2f,
                1.2f,
                (centerX - bitmapWidth / 2).toFloat(),
                (centerY - bitmapHeight / 2).toFloat()
            )
            path.addCircle(
                centerX.toFloat(),
                centerY.toFloat(),
                radius * (fraction - 0.1f) * 2,
                Path.Direction.CW
            )
            canvas.concat(matrix)
            canvas.clipPath(path)
            canvas.drawBitmap(
                bitmap,
                (centerX - bitmapWidth).toFloat(),
                (centerY - bitmapHeight).toFloat(),
                paint
            )
        } else {
            val matrix = Matrix()
            matrix.postScale(
                1.2f + 0.5f * (fraction - 0.6f) * 2.5f,
                1.2f + 0.5f * (fraction - 0.6f) * 2.5f, (
                        centerX - bitmapWidth / 2).toFloat(), (centerY - bitmapHeight / 2).toFloat()
            )
            canvas.concat(matrix)
            canvas.drawBitmap(
                bitmap,
                (centerX - bitmapWidth).toFloat(),
                (centerY - bitmapHeight).toFloat(),
                paint
            )
        }
        canvas.restore()
    }

    override fun drawAppStatement(
        canvas: Canvas, fraction: Float, statement: String, colorOfStatement: Int,
        widthAndHeightOfView: WidthAndHeightOfView?
    ) {
        canvas.save()
        val width: Int = widthAndHeightOfView?.width ?: 0
        val height: Int = widthAndHeightOfView?.height ?: 0
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = colorOfStatement
        paint.style = Paint.Style.STROKE
        paint.textSize = 45f
        paint.textSkewX = -0.2f
        paint.textAlign = Paint.Align.CENTER
        val rectF = RectF(
            (width / 4 - statement.length).toFloat(), (height * 7 / 8).toFloat(),
            (width * 3).toFloat(), height.toFloat()
        )
        if (fraction <= 0.60f) {
            val path = Path()
            path.addArc(rectF, 193f, 40 * fraction * 1.67f)
            canvas.drawPath(path, paint)
        } else {
            val path = Path()
            path.addArc(rectF, 193f, 40f)
            canvas.drawPath(path, paint)
            canvas.drawTextOnPath(statement, path, 0f, 0f, paint)
        }
        canvas.restore()
    }
}