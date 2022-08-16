package com.zjh.ktwanandroid.app.skin.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.DrawableRes
import androidx.customview.widget.ExploreByTouchHelper.INVALID_ID
import com.google.android.material.bottomnavigation.BottomNavigationView
import skin.support.content.res.SkinCompatResources
import skin.support.widget.SkinCompatBackgroundHelper
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatSupportable
import com.google.android.material.R


/**
 * @author zjh
 * 2022/8/8
 */
class SkinMaterialBottomNavigationView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    BottomNavigationView(context, attrs, defStyleAttr), SkinCompatSupportable {
    private val mBackgroundTintHelper: SkinCompatBackgroundHelper?
    private var mTextColorResId: Int = INVALID_ID
    private var mIconTintResId: Int = INVALID_ID
    private var mDefaultTintResId: Int = INVALID_ID

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun setBackgroundResource(@DrawableRes resId: Int) {
        super.setBackgroundResource(resId)
        mBackgroundTintHelper?.onSetBackgroundResource(resId)
    }

    private fun applyItemTextColorResource() {
        mTextColorResId = SkinCompatHelper.checkResourceId(mTextColorResId)
        if (mTextColorResId != INVALID_ID) {
            itemTextColor = SkinCompatResources.getColorStateList(context, mTextColorResId)
        } else {
            mDefaultTintResId = SkinCompatHelper.checkResourceId(mDefaultTintResId)
            if (mDefaultTintResId != INVALID_ID) {
                itemTextColor = createDefaultColorStateList(android.R.attr.textColorSecondary)
            }
        }
    }

    private fun applyItemIconTintResource() {
        mIconTintResId = SkinCompatHelper.checkResourceId(mIconTintResId)
        if (mIconTintResId != INVALID_ID) {
            itemIconTintList = SkinCompatResources.getColorStateList(context, mIconTintResId)
        } else {
            mDefaultTintResId = SkinCompatHelper.checkResourceId(mDefaultTintResId)
            if (mDefaultTintResId != INVALID_ID) {
                itemIconTintList = createDefaultColorStateList(android.R.attr.textColorSecondary)
            }
        }
    }

    private fun createDefaultColorStateList(baseColorThemeAttr: Int): ColorStateList? {
        val value = TypedValue()
        if (!context.theme.resolveAttribute(baseColorThemeAttr, value, true)) {
            return null
        }
        val baseColor = SkinCompatResources.getColorStateList(context, value.resourceId)
        val colorPrimary = SkinCompatResources.getColor(context, mDefaultTintResId)
        val defaultColor = baseColor.defaultColor
        return ColorStateList(
            arrayOf(
                DISABLED_STATE_SET,
                CHECKED_STATE_SET,
                EMPTY_STATE_SET
            ), intArrayOf(
                baseColor.getColorForState(DISABLED_STATE_SET, defaultColor),
                colorPrimary,
                defaultColor
            )
        )
    }

    private fun resolveColorPrimary(): Int {
        val value = TypedValue()
        return if (!context.theme.resolveAttribute(
                android.R.attr.colorPrimary, value, true
            )
        ) {
            INVALID_ID
        } else value.resourceId
    }

    override fun applySkin() {
        mBackgroundTintHelper?.applySkin()
        applyItemIconTintResource()
        applyItemTextColorResource()
    }

    companion object {
        private val DISABLED_STATE_SET = intArrayOf(-android.R.attr.state_enabled)
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }

    init {
        mBackgroundTintHelper = SkinCompatBackgroundHelper(this)
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr)
        val a: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.BottomNavigationView, defStyleAttr,
            R.style.Widget_Design_BottomNavigationView
        )
        mDefaultTintResId = resolveColorPrimary()
        a.recycle()
        applyItemIconTintResource()
        applyItemTextColorResource()
    }
}
