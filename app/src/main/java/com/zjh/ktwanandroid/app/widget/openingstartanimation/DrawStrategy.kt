package com.zjh.ktwanandroid.app.widget.openingstartanimation
import android.graphics.Canvas
import android.graphics.drawable.Drawable

/**
 * @author Jackdow
 * @version 1.0
 * FancyView
 */
interface DrawStrategy {
    /**
     * 绘制app名称文字
     * @param canvas 画布
     * @param fraction 完成时间百分比
     * @param colorOfAppName 字体颜色
     * @param name 文字
     * @param widthAndHeightOfView view的宽和高
     */
    fun drawAppName(
        canvas: Canvas, fraction: Float, name: String, colorOfAppName: Int,
        widthAndHeightOfView: WidthAndHeightOfView?
    )

    /**
     * 绘制app图标
     * @param canvas 画布
     * @param fraction 完成时间百分比
     * @param colorOfIcon 绘制图标颜色
     * @param icon 图标
     * @param widthAndHeightOfView view的宽和高
     */
    fun drawAppIcon(
        canvas: Canvas, fraction: Float, icon: Drawable?, colorOfIcon: Int,
        widthAndHeightOfView: WidthAndHeightOfView?
    )

    /**
     * 绘制app一句话描述
     * @param canvas 画布
     * @param fraction 完成时间百分比
     * @param statement 一句话描述
     * @param colorOfStatement 字体颜色
     * @param widthAndHeightOfView view的宽和高
     */
    fun drawAppStatement(
        canvas: Canvas, fraction: Float, statement: String, colorOfStatement: Int,
        widthAndHeightOfView: WidthAndHeightOfView?
    )
}