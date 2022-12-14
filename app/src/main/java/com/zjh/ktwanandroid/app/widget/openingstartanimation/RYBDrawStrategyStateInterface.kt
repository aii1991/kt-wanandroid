package com.zjh.ktwanandroid.app.widget.openingstartanimation

import android.graphics.Canvas
import android.graphics.drawable.Drawable

/**
 * @author Jackdow
 * @version 1.0
 * FancyView
 */
interface RYBDrawStrategyStateInterface {
    /**
     * 绘制图标
     * @param canvas 画布
     * @param fraction 完成时间
     * @param drawable 图标素材
     * @param colorOfIcon 绘制颜色
     * @param widthAndHeightOfView view的宽高
     */
    fun drawIcon(canvas: Canvas, fraction: Float, drawable: Drawable, colorOfIcon: Int, widthAndHeightOfView: WidthAndHeightOfView)
}