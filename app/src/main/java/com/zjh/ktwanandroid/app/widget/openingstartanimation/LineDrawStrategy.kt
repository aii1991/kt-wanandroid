package com.zjh.ktwanandroid.app.widget.openingstartanimation

import android.graphics.*
import android.graphics.drawable.Drawable
import com.zjh.ktwanandroid.app.widget.openingstartanimation.util.BitmapUtils.drawableToBitmap

/**
 * @author Jackdow
 * @version 1.0
 * FancyView
 */
class LineDrawStrategy : DrawStrategy {
    override fun drawAppName(
        canvas: Canvas, fraction: Float, name: String, colorOfAppName: Int,
        widthAndHeightOfView: WidthAndHeightOfView?
    ) {
        canvas.save()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = colorOfAppName
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        paint.textSize = 50f
        paint.strokeJoin = Paint.Join.ROUND
        paint.textAlign = Paint.Align.LEFT
        val x: Float = if(widthAndHeightOfView == null) 0f else (widthAndHeightOfView.width / 2).toFloat()
        val centerY: Int = if(widthAndHeightOfView == null) 0 else widthAndHeightOfView.height / 2
        val y = (centerY - 275).toFloat()
        val path = Path()
        path.moveTo(x, y)
        if (fraction <= 0.50) {
            path.lineTo(x, y + (25 + name.length + 250) * (fraction / 0.50f))
            canvas.drawPath(path, paint)
        } else {
            path.lineTo(x, y + (25 + name.length + 250) * ((1 - fraction) / 0.50f))
            canvas.drawPath(path, paint)
            paint.style = Paint.Style.FILL
            canvas.drawText(name, x + 20, y + 150, paint)
        }
        canvas.restore()
    }

    override fun drawAppIcon(
        canvas: Canvas, fraction: Float, icon: Drawable?, colorOfIcon: Int,
        widthAndHeightOfView: WidthAndHeightOfView?
    ) {
        val centerX: Int = if(widthAndHeightOfView == null) 0 else widthAndHeightOfView.width / 2
        val centerY: Int = if(widthAndHeightOfView == null) 0 else widthAndHeightOfView.height / 2
        val bitmap = drawableToBitmap(icon!!)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = colorOfIcon
        paint.strokeWidth = 3f
        paint.strokeJoin = Paint.Join.ROUND
        paint.style = Paint.Style.STROKE
        val bitmapLeft = (centerX - 250).toFloat()
        val bitmapRight = bitmapLeft + bitmap.width * 1.7f
        val bitmapTop = (centerY - 250).toFloat()
        val bitmapBottom = bitmapTop + bitmap.height * 1.7f
        canvas.save()
        if (fraction <= 0.75) {
            val newfraction = fraction / 0.75f
            if (newfraction <= 0.25) {
                canvas.drawLine(
                    bitmapLeft, bitmapBottom, bitmapLeft,
                    bitmapBottom - (bitmapBottom - bitmapTop) * (newfraction / 0.25f), paint
                )
                //  path.lineTo(bitmapLeft, bitmapBottom + (bitmapBottom - bitmapTop) * (newfraction / 0.25f));
            } else {
                canvas.drawLine(bitmapLeft, bitmapBottom, bitmapLeft, bitmapTop, paint)
                //  path.lineTo(bitmapLeft, bitmapTop);
            }
            if (newfraction > 0.25) {
                if (newfraction <= 0.50) {
                    canvas.drawLine(
                        bitmapLeft, bitmapTop,
                        bitmapLeft + (bitmapRight - bitmapLeft) * ((newfraction - 0.25f) / 0.25f),
                        bitmapTop, paint
                    )
                    //  path.lineTo(bitmapLeft + (bitmapRight - bitmapLeft) * ((newfraction - 0.25f)/0.25f),
                    //         bitmapTop);
                } else {
                    canvas.drawLine(bitmapLeft, bitmapTop, bitmapRight, bitmapTop, paint)
                    // path.lineTo(bitmapRight, bitmapTop);
                }
            }
            if (newfraction > 0.50) {
                if (newfraction <= 0.75) {
                    canvas.drawLine(
                        bitmapRight, bitmapTop, bitmapRight,
                        bitmapTop + (bitmapBottom - bitmapTop) * ((newfraction - 0.50f) / 0.25f),
                        paint
                    )
                    //path.lineTo(bitmapRight, bitmapTop + (bitmapBottom - bitmapTop) * ((fraction - 0.50f) / 0.25f));
                } else {
                    canvas.drawLine(bitmapRight, bitmapTop, bitmapRight, bitmapBottom, paint)
                    //path.lineTo(bitmapRight, bitmapBottom);
                }
            }
            if (newfraction > 0.75) {
                if (newfraction <= 1) {
                    canvas.drawLine(
                        bitmapRight,
                        bitmapBottom,
                        bitmapRight - (bitmapRight - bitmapLeft) * ((newfraction - 0.75f) / 0.25f),
                        bitmapBottom,
                        paint
                    )
                    // path.lineTo(bitmapLeft + (bitmapRight - bitmapLeft) * ((fraction - 0.75f)/ 0.25f),
                    //         bitmapBottom);
                } else {
                    canvas.drawLine(bitmapRight, bitmapBottom, bitmapLeft, bitmapBottom, paint)
                    // path.lineTo(bitmapLeft, bitmapBottom);
                }
            }
        }
        canvas.restore()
        canvas.save()
        if (fraction > 0.75) {
            canvas.clipRect(
                bitmapLeft + bitmap.width / 2f * ((1 - fraction) / 0.25f),
                bitmapTop + bitmap.height / 2f * ((1 - fraction) / 0.25f),
                bitmapRight - bitmap.width / 2f * ((1 - fraction) / 0.25f),
                bitmapBottom - bitmap.height / 2f * ((1 - fraction) / 0.25f)
            )
            val matrix = Matrix()
            matrix.postScale(
                1.7f, 1.7f, (bitmapLeft + bitmapRight) * 0.5f,
                (bitmapTop + bitmapBottom) * 0.5f
            )
            canvas.concat(matrix)
            canvas.drawBitmap(
                bitmap, (bitmapLeft + bitmapRight) / 2 - bitmap.width / 2,
                (bitmapTop + bitmapBottom) / 2 - bitmap.height / 2, paint
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
