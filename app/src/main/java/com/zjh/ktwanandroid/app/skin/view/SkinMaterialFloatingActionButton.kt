package com.zjh.ktwanandroid.app.skin.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.customview.widget.ExploreByTouchHelper.INVALID_ID
import com.google.android.material.floatingactionbutton.FloatingActionButton
import skin.support.content.res.SkinCompatResources
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatImageHelper
import skin.support.widget.SkinCompatSupportable
import com.google.android.material.R


/**
 * @author zjh
 * 2022/8/8
 */
class SkinMaterialFloatingActionButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FloatingActionButton(context, attrs, defStyleAttr), SkinCompatSupportable {
    private var mRippleColorResId: Int = INVALID_ID
    private var mBackgroundTintResId: Int = INVALID_ID
    private val mImageHelper: SkinCompatImageHelper?

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    private fun applyBackgroundTintResource() {
        mBackgroundTintResId = SkinCompatHelper.checkResourceId(mBackgroundTintResId)
        if (mBackgroundTintResId != INVALID_ID) {
            backgroundTintList = SkinCompatResources.getColorStateList(
                context,
                mBackgroundTintResId
            )
        }
    }

    private fun applyRippleColorResource() {
        mRippleColorResId = SkinCompatHelper.checkResourceId(mRippleColorResId)
        if (mRippleColorResId != INVALID_ID) {
            rippleColor = SkinCompatResources.getColor(context, mRippleColorResId)
        }
    }

    override fun applySkin() {
        applyBackgroundTintResource()
        applyRippleColorResource()
        mImageHelper?.applySkin()
    }

    init {
        val a: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.FloatingActionButton, defStyleAttr,
            R.style.Widget_Design_FloatingActionButton
        )
        mBackgroundTintResId =
            a.getResourceId(R.styleable.FloatingActionButton_backgroundTint, INVALID_ID)
        mRippleColorResId =
            a.getResourceId(R.styleable.FloatingActionButton_rippleColor, INVALID_ID)
        a.recycle()
        applyBackgroundTintResource()
        applyRippleColorResource()
        mImageHelper = SkinCompatImageHelper(this)
        mImageHelper.loadFromAttributes(attrs, defStyleAttr)
    }
}
