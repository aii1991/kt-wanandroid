package com.zjh.ktwanandroid.app.skin.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.customview.widget.ExploreByTouchHelper.INVALID_ID
import com.google.android.material.appbar.CollapsingToolbarLayout
import skin.support.content.res.SkinCompatVectorResources
import skin.support.widget.SkinCompatBackgroundHelper
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatSupportable
import com.google.android.material.R


/**
 * @author zjh
 * 2022/8/8
 */
class SkinMaterialCollapsingToolbarLayout(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
) :
    CollapsingToolbarLayout(context, attrs, defStyleAttr), SkinCompatSupportable {
    private var mContentScrimResId: Int = INVALID_ID
    private var mStatusBarScrimResId: Int = INVALID_ID
    private val mBackgroundTintHelper: SkinCompatBackgroundHelper?

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    private fun applyStatusBarScrimResource() {
        mStatusBarScrimResId = SkinCompatHelper.checkResourceId(mStatusBarScrimResId)
        if (mStatusBarScrimResId != INVALID_ID) {
            val drawable =
                SkinCompatVectorResources.getDrawableCompat(context, mStatusBarScrimResId)
            drawable?.let { statusBarScrim = it }
        }
    }

    private fun applyContentScrimResource() {
        mContentScrimResId = SkinCompatHelper.checkResourceId(mContentScrimResId)
        if (mContentScrimResId != INVALID_ID) {
            val drawable =
                SkinCompatVectorResources.getDrawableCompat(context, mContentScrimResId)
            drawable?.let { contentScrim = it }
        }
    }

    override fun applySkin() {
        applyContentScrimResource()
        applyStatusBarScrimResource()
        mBackgroundTintHelper?.applySkin()
    }

    init {
        val a: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CollapsingToolbarLayout, defStyleAttr,
            R.style.Widget_Design_CollapsingToolbar
        )
        mContentScrimResId =
            a.getResourceId(R.styleable.CollapsingToolbarLayout_contentScrim, INVALID_ID)
        mStatusBarScrimResId =
            a.getResourceId(R.styleable.CollapsingToolbarLayout_statusBarScrim, INVALID_ID)
        a.recycle()
        applyContentScrimResource()
        applyStatusBarScrimResource()
        mBackgroundTintHelper = SkinCompatBackgroundHelper(this)
        mBackgroundTintHelper.loadFromAttributes(attrs, 0)
    }
}
