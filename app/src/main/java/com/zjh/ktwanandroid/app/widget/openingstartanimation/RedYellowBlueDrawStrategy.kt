package com.zjh.ktwanandroid.app.widget.openingstartanimation
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.Drawable

/**
 * @author Jackdow
 * @version 1.0
 * FancyView
 */
class RedYellowBlueDrawStrategy : DrawStrategy {
    private val mRYBDrawStrategyStateController: RYBDrawStrategyStateController = RYBDrawStrategyStateController()

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
        mRYBDrawStrategyStateController.choseStateDrawIcon(
            canvas,
            fraction,
            icon!!,
            colorOfIcon,
            widthAndHeightOfView!!
        )
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