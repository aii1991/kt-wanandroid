package com.zjh.ktwanandroid.app.skin.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.customview.widget.ExploreByTouchHelper.INVALID_ID
import com.google.android.material.navigation.NavigationView
import skin.support.content.res.SkinCompatResources
import skin.support.content.res.SkinCompatThemeUtils
import skin.support.content.res.SkinCompatV7ThemeUtils
import skin.support.content.res.SkinCompatVectorResources
import skin.support.widget.SkinCompatBackgroundHelper
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatSupportable
import com.google.android.material.R


/**
 * @author zjh
 * 2022/8/8
 */
class SkinMaterialNavigationView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    NavigationView(context, attrs, defStyleAttr), SkinCompatSupportable {
    private var mItemBackgroundResId: Int = INVALID_ID
    private var mTextColorResId: Int = INVALID_ID
    private var mDefaultTintResId: Int = INVALID_ID
    private var mIconTintResId: Int = INVALID_ID
    private val mBackgroundTintHelper: SkinCompatBackgroundHelper?

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun setItemBackgroundResource(@DrawableRes resId: Int) {
        super.setItemBackgroundResource(resId)
        mItemBackgroundResId = resId
        applyItemBackgroundResource()
    }

    private fun applyItemBackgroundResource() {
        mItemBackgroundResId = SkinCompatHelper.checkResourceId(mItemBackgroundResId)
        if (mItemBackgroundResId == INVALID_ID) {
            return
        }
        val drawable =
            SkinCompatVectorResources.getDrawableCompat(context, mItemBackgroundResId)
        drawable?.let { itemBackground = it }
    }

    override fun setItemTextAppearance(@StyleRes resId: Int) {
        super.setItemTextAppearance(resId)
        if (resId != INVALID_ID) {
            val a: TypedArray =
                context.obtainStyledAttributes(resId, skin.support.R.styleable.SkinTextAppearance)
            if (a.hasValue(skin.support.R.styleable.SkinTextAppearance_android_textColor)) {
                mTextColorResId =
                    a.getResourceId(skin.support.R.styleable.SkinTextAppearance_android_textColor, INVALID_ID)
            }
            a.recycle()
            applyItemTextColorResource()
        }
    }

    private fun applyItemTextColorResource() {
        mTextColorResId = SkinCompatHelper.checkResourceId(mTextColorResId)
        if (mTextColorResId != INVALID_ID) {
            setItemTextColor(SkinCompatResources.getColorStateList(context, mTextColorResId))
        } else {
            mDefaultTintResId = SkinCompatHelper.checkResourceId(mDefaultTintResId)
            if (mDefaultTintResId != INVALID_ID) {
                itemTextColor = createDefaultColorStateList(android.R.attr.textColorPrimary)
            }
        }
    }

    private fun applyItemIconTintResource() {
        mIconTintResId = SkinCompatHelper.checkResourceId(mIconTintResId)
        if (mIconTintResId != INVALID_ID) {
            setItemIconTintList(SkinCompatResources.getColorStateList(context, mIconTintResId))
        } else {
            mDefaultTintResId = SkinCompatHelper.checkResourceId(mDefaultTintResId)
            if (mDefaultTintResId != INVALID_ID) {
                setItemIconTintList(createDefaultColorStateList(android.R.attr.textColorSecondary))
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

    override fun applySkin() {
        mBackgroundTintHelper?.applySkin()
        applyItemIconTintResource()
        applyItemTextColorResource()
        applyItemBackgroundResource()
    }

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
        private val DISABLED_STATE_SET = intArrayOf(-android.R.attr.state_enabled)
    }

    init {
        mBackgroundTintHelper = SkinCompatBackgroundHelper(this)
        mBackgroundTintHelper.loadFromAttributes(attrs, 0)
        val a: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.NavigationView, defStyleAttr,
            R.style.Widget_Design_NavigationView
        )
        if (a.hasValue(R.styleable.NavigationView_itemIconTint)) {
            mIconTintResId = a.getResourceId(R.styleable.NavigationView_itemIconTint, INVALID_ID)
        } else {
            mDefaultTintResId = SkinCompatV7ThemeUtils.getColorPrimaryResId(context)
        }
        if (a.hasValue(R.styleable.NavigationView_itemTextAppearance)) {
            val textAppearance =
                a.getResourceId(R.styleable.NavigationView_itemTextAppearance, INVALID_ID)
            if (textAppearance != INVALID_ID) {
                val ap: TypedArray =
                    context.obtainStyledAttributes(textAppearance, skin.support.R.styleable.SkinTextAppearance)
                if (ap.hasValue(skin.support.R.styleable.SkinTextAppearance_android_textColor)) {
                    mTextColorResId = ap.getResourceId(
                        skin.support.R.styleable.SkinTextAppearance_android_textColor,
                        INVALID_ID
                    )
                }
                ap.recycle()
            }
        }
        if (a.hasValue(R.styleable.NavigationView_itemTextColor)) {
            mTextColorResId = a.getResourceId(R.styleable.NavigationView_itemTextColor, INVALID_ID)
        } else {
            mDefaultTintResId = SkinCompatV7ThemeUtils.getColorPrimaryResId(context)
        }
        if (mTextColorResId == INVALID_ID) {
            mTextColorResId = SkinCompatThemeUtils.getTextColorPrimaryResId(context)
        }
        mItemBackgroundResId =
            a.getResourceId(R.styleable.NavigationView_itemBackground, INVALID_ID)
        a.recycle()
        applyItemIconTintResource()
        applyItemTextColorResource()
        applyItemBackgroundResource()
    }
}
