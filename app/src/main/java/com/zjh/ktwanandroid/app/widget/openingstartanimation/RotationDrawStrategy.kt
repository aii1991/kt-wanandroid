package com.zjh.ktwanandroid.app.widget.openingstartanimation
import android.graphics.*
import android.graphics.drawable.Drawable
import com.zjh.ktwanandroid.app.widget.openingstartanimation.util.BitmapUtils.drawableToBitmap

/**
 * @author Jackdow
 * @version 1.0
 * FancyView
 */
class RotationDrawStrategy : DrawStrategy {
    override fun drawAppName(
        canvas: Canvas, fraction: Float, name: String, colorOfAppName: Int,
        widthAndHeightOfView: WidthAndHeightOfView?
    ) {
        canvas.save()
        val width: Int = widthAndHeightOfView?.width ?: 0
        val height: Int = widthAndHeightOfView?.height ?: 0
        canvas.clipRect(
            (width / 2 - 150).toFloat(), (height / 2 - 80).toFloat(),
            width / 2 + 150 * fraction, height / 2 + 65 * fraction
        )
        val paint = Paint()
        paint.color = colorOfAppName
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 50f
        canvas.drawText(name!!, (width / 2).toFloat(), (height / 2 + 50).toFloat(), paint)
        canvas.restore()
    }

    override fun drawAppIcon(
        canvas: Canvas, fraction: Float, icon: Drawable?, colorOfIcon: Int,
        widthAndHeightOfView: WidthAndHeightOfView?
    ) {
        drawIcon(
            canvas, (fraction * 540).toInt(), drawableToBitmap(icon!!),
            widthAndHeightOfView?.width ?: 0,  widthAndHeightOfView?.height ?: 0
        )
    }

    override fun drawAppStatement(
        canvas: Canvas, fraction: Float, statement: String, colorOfStatement: Int,
        widthAndHeightOfView: WidthAndHeightOfView?
    ) {
        canvas.save()
        val width: Int =  widthAndHeightOfView?.width ?: 0
        val height: Int =  widthAndHeightOfView?.height ?: 0
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

    /**
     * 根据角度绘制图标的类
     * @param canvas 画布
     * @param degree 角度
     * @param icon 图标
     * @param width view宽度
     * @param height view高度
     */
    private fun drawIcon(canvas: Canvas, degree: Int, icon: Bitmap, width: Int, height: Int) {
        if (degree <= 180) {
            drawIconOne(canvas, degree / 180f, icon, width, height)
        }
        if (degree > 180 && degree <= 360) {
            drawIconTwo(canvas, degree - 180, icon, width, height)
        }
        if (degree > 360 && degree <= 540) {
            drawIconThree(canvas, degree - 360, icon, width, height)
        }
    }

    /**
     * 根据角度绘制图标的类
     * @param canvas 画布
     * @param fraction 完成时间百分比
     * @param icon 图标
     * @param width view宽度
     * @param height view高度
     */
    private fun drawIconOne(
        canvas: Canvas,
        fraction: Float,
        icon: Bitmap,
        width: Int,
        height: Int
    ) {
        val camera = Camera()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.save()
        val centerX = width / 2
        val centerY = height / 2 - 200
        val x = centerX - icon.width / 2
        val y = centerY - icon.height / 2
        val matrix = Matrix()
        matrix.postScale(1.7f, 1.7f, centerX.toFloat(), centerY.toFloat())
        canvas.concat(matrix)
        canvas.clipRect(
            x.toFloat(),
            y.toFloat(),
            x + icon.width * fraction * 0.5f,
            y + icon.height * fraction * 0.5f
        )
        canvas.translate(centerX.toFloat(), centerY.toFloat())
        camera.save()
        camera.rotateX(180f)
        camera.rotateY(-180f)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.translate(-centerX.toFloat(), -centerY.toFloat())
        canvas.drawBitmap(icon, x.toFloat(), y.toFloat(), paint)
        canvas.restore()
    }

    /**
     * 根据角度绘制图标的类
     * @param canvas 画布
     * @param degree 角度
     * @param icon 图标
     * @param width view宽度
     * @param height view高度
     */
    private fun drawIconTwo(canvas: Canvas, degree: Int, icon: Bitmap, width: Int, height: Int) {
        val camera = Camera()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val centerX = width / 2
        val centerY = height / 2 - 200
        val x = centerX - icon.width / 2
        val y = centerY - icon.height / 2
        val matrix = Matrix()
        matrix.postScale(1.7f, 1.7f, centerX.toFloat(), centerY.toFloat())

        //绘制左半部分
        canvas.save()
        canvas.concat(matrix)
        canvas.clipRect(x, y, x + icon.width / 2, y + icon.height / 2)
        canvas.translate(centerX.toFloat(), centerY.toFloat())
        camera.save()
        camera.rotateX(180f)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.translate(-centerX.toFloat(), -centerY.toFloat())
        canvas.drawBitmap(icon, x.toFloat(), y.toFloat(), paint)
        canvas.restore()

        //绘制右半部分
        canvas.save()
        canvas.concat(matrix)
        if (degree <= 90) canvas.clipRect(
            x,
            y,
            x + icon.width / 2,
            y + icon.height / 2
        ) else canvas.clipRect(x + icon.width / 2, y, x + icon.width, y + icon.height / 2)
        canvas.translate(centerX.toFloat(), centerY.toFloat())
        camera.save()
        camera.rotateX(180f)
        camera.rotateY((180 - degree).toFloat())
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.translate(-centerX.toFloat(), -centerY.toFloat())
        canvas.drawBitmap(icon, x.toFloat(), y.toFloat(), paint)
        canvas.restore()
    }

    /**
     * 根据角度绘制图标的类
     * @param canvas 画布
     * @param degree 角度
     * @param icon 图标
     * @param width view宽度
     * @param height view高度
     */
    private fun drawIconThree(canvas: Canvas, degree: Int, icon: Bitmap, width: Int, height: Int) {
        val camera = Camera()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val centerX = width / 2
        val centerY = height / 2 - 200
        val x = centerX - icon.width / 2
        val y = centerY - icon.height / 2
        val matrix = Matrix()
        matrix.postScale(1.7f, 1.7f, centerX.toFloat(), centerY.toFloat())
        //绘制上半部分
        canvas.save()
        canvas.concat(matrix)
        canvas.clipRect(x, y, x + icon.width, y + icon.height / 2)
        canvas.drawBitmap(icon, x.toFloat(), y.toFloat(), paint)
        canvas.restore()

        //绘制下半部分
        canvas.save()
        canvas.concat(matrix)
        if (degree <= 90) canvas.clipRect(
            x,
            y,
            x + icon.width,
            y + icon.height / 2
        ) else canvas.clipRect(x, y + icon.height / 2, x + icon.width, y + icon.height)
        canvas.translate(centerX.toFloat(), centerY.toFloat())
        camera.save()
        camera.rotateX((180 - degree).toFloat())
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.translate(-centerX.toFloat(), -centerY.toFloat())
        canvas.drawBitmap(icon, x.toFloat(), y.toFloat(), paint)
        canvas.restore()
    }
}