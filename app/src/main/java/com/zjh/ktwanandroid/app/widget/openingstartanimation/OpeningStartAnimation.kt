package com.zjh.ktwanandroid.app.widget.openingstartanimation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.SoftReference
import java.util.*

/**
 * @author Jackdow
 * @version 1.0
 * FancyView
 */
class OpeningStartAnimation private constructor(context: Context) : View(context) {
    private var animationInterval: Long = 1500
    private var animationFinishTime: Long = 350
    private var mWidthAndHeightOfView: WidthAndHeightOfView? = null
    private var colorOfBackground = Color.WHITE
    private var fraction = 0f
    private var mDrawable: Drawable? = null
    private var colorOfAppIcon = Color.parseColor("#00897b")
    private var appName = ""
    private var colorOfAppName = Color.parseColor("#00897b")
    private var appStatement = ""
    private var colorOfAppStatement = Color.parseColor("#607D8B")
    private var mDelegateRecycleView: DelegateRecycleView? = null
    private var mDrawStrategy: DrawStrategy = NormalDrawStrategy()
    private var mAnimationListener: Animator.AnimatorListener? = null

    /**
     * 设置完成的百分比
     * @param fraction 百分比
     */
    private fun setFraction(fraction: Float) {
        this.fraction = fraction
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(colorOfBackground) //绘制背景色
        super.onDraw(canvas)
        mWidthAndHeightOfView?.height = height
        mWidthAndHeightOfView?.width = width
        mDrawStrategy.drawAppIcon(
            canvas, fraction, mDrawable, colorOfAppIcon,
            mWidthAndHeightOfView
        )
        mDrawStrategy.drawAppName(
            canvas, fraction, appName, colorOfAppName,
            mWidthAndHeightOfView
        )
        mDrawStrategy.drawAppStatement(
            canvas, fraction, appStatement, colorOfAppStatement,
            mWidthAndHeightOfView
        )
    }

    /**
     * 显示动画
     * @param mactivity 显示动画的界面
     */
    fun show(mactivity: Activity) {
        val softReference = SoftReference(mactivity)
        val activity = softReference.get()
        val decorView = activity!!.window.decorView
        decorView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        if (activity is AppCompatActivity) {
            val actionBar: ActionBar? = activity.supportActionBar
            actionBar?.hide()
        } else {
            val actionBar = activity.actionBar
            actionBar?.hide()
        }
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        activity.addContentView(this, layoutParams)
        val objectAnimator = ObjectAnimator.ofFloat(this, "fraction", 0f, 1f)
        objectAnimator.duration = animationInterval - 50
        if(mAnimationListener != null){
            objectAnimator.addListener(mAnimationListener)
        }
        objectAnimator.start()
        //处理动画定时
        val handler = Handler { message ->
            if (message.what == FINISHANIMATION) {
                moveAnimation(activity)
            }
            false
        }
        //动画定时器
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val message = Message()
                message.what = FINISHANIMATION
                handler.sendMessage(message)
            }
        }, animationInterval)
    }

    /**
     * 隐藏动画view
     * @param activity 当前活动
     */
    private fun moveAnimation(activity: Activity?) {
        val decorView = activity!!.window.decorView
        decorView.systemUiVisibility = SYSTEM_UI_FLAG_VISIBLE
        if (activity is AppCompatActivity) {
            val actionBar: ActionBar? = activity.supportActionBar
            if (actionBar != null) actionBar.show()
        } else {
            val actionBar = activity.actionBar
            actionBar?.show()
        }
        animate()
            .scaleX(0f)
            .scaleY(0f)
            .withLayer()
            .alpha(0f).duration = animationFinishTime
        mDelegateRecycleView?.finishAnimation()
    }

    /**
     * 使用Builder模式创建对象
     */
    class Builder(context: Context) : DelegateRecycleView {
        var mOpeningStartAnimation: OpeningStartAnimation?

        /**
         * 设置动画时间的方法
         * @param animationInterval 动画时间
         * @return Builder对象
         */
        fun setAnimationInterval(animationInterval: Long): Builder {
            mOpeningStartAnimation!!.animationInterval = animationInterval
            return this
        }

        fun setAnimationListener(animationListener:Animator.AnimatorListener): Builder{
            mOpeningStartAnimation!!.mAnimationListener = animationListener
            return this
        }

        /**
         * 设置动画消失的时间
         * @param animationFinishTime 动画消失的时间
         * @return Builder对象
         */
        fun setAnimationFinishTime(animationFinishTime: Long): Builder {
            mOpeningStartAnimation!!.animationFinishTime = animationFinishTime
            return this
        }

        /**
         * 设置动画图标
         * @param drawable 动画图标
         * @return Builder对象
         */
        fun setAppIcon(drawable: Drawable?): Builder {
            mOpeningStartAnimation!!.mDrawable = drawable
            return this
        }

        /**
         * 设置图标绘制辅助颜色
         * @param colorOfAppIcon 辅助颜色
         * @return Builder对象
         */
        fun setColorOfAppIcon(colorOfAppIcon: Int): Builder {
            mOpeningStartAnimation!!.colorOfAppIcon = colorOfAppIcon
            return this
        }

        /**
         * 设置要绘制的app名称
         * @param appName app名称
         * @return Builder对象
         */
        fun setAppName(appName: String): Builder {
            mOpeningStartAnimation!!.appName = appName
            return this
        }

        /**
         * 设置app名称的颜色
         * @param colorOfAppName app名称颜色
         * @return Builder对象
         */
        fun setColorOfAppName(colorOfAppName: Int): Builder {
            mOpeningStartAnimation!!.colorOfAppName = colorOfAppName
            return this
        }

        /**
         * 设置app一句话描述
         * @param appStatement app一句话描述
         * @return Builder对象
         */
        fun setAppStatement(appStatement: String): Builder {
            mOpeningStartAnimation!!.appStatement = appStatement
            return this
        }

        /**
         * 设置一句话描述的字体颜色
         * @param colorOfAppStatement 字体颜色
         * @return Builder对象
         */
        fun setColorOfAppStatement(colorOfAppStatement: Int): Builder {
            mOpeningStartAnimation!!.colorOfAppStatement = colorOfAppStatement
            return this
        }

        /**
         * 设置背景颜色
         * @param colorOfBackground 背景颜色对应的int值
         * @return Builder对象
         */
        fun setColorOfBackground(colorOfBackground: Int): Builder {
            mOpeningStartAnimation!!.colorOfBackground = colorOfBackground
            return this
        }

        /**
         * 开放绘制策略接口，可由用户自行定义
         * @param drawStrategy 绘制接口
         * @return Builder对象
         */
        fun setDrawStategy(drawStrategy: DrawStrategy): Builder {
            mOpeningStartAnimation!!.mDrawStrategy = drawStrategy
            return this
        }

        /**
         * 创建开屏动画
         * @return 创建出的开屏动画
         */
        fun create(): OpeningStartAnimation {
            return mOpeningStartAnimation!!
        }

        override fun finishAnimation() {
            mOpeningStartAnimation = null
        }

        init {
            mOpeningStartAnimation = OpeningStartAnimation(context)
            mOpeningStartAnimation!!.mDelegateRecycleView = this
        }
    }

    companion object {
        private const val FINISHANIMATION = 1
    }

    init {
        val packageManager = context.packageManager
        mDrawable = context.applicationInfo.loadIcon(packageManager)
        appName = packageManager.getApplicationLabel(context.applicationInfo) as String
        appStatement = "Sample Statement"
    }

    init {
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        mWidthAndHeightOfView = WidthAndHeightOfView()
    }
}