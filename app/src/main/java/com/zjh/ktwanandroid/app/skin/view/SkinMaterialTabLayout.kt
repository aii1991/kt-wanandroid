package com.zjh.ktwanandroid.app.skin.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout
import com.google.android.material.R
import skin.support.content.res.SkinCompatResources
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatHelper.INVALID_ID
import skin.support.widget.SkinCompatSupportable


/**
 * @author zjh
 * 2022/8/8
 */
class SkinMaterialTabLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    TabLayout(context, attrs, defStyleAttr), SkinCompatSupportable {
    private var mTabIndicatorColorResId: Int = INVALID_ID
    private var mTabTextColorsResId: Int = INVALID_ID
    private var mTabSelectedTextColorResId: Int = INVALID_ID

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun applySkin() {
        mTabIndicatorColorResId = SkinCompatHelper.checkResourceId(mTabIndicatorColorResId)
        if (mTabIndicatorColorResId != INVALID_ID) {
            setSelectedTabIndicatorColor(
                SkinCompatResources.getColor(
                    context,
                    mTabIndicatorColorResId
                )
            )
        }
        mTabTextColorsResId = SkinCompatHelper.checkResourceId(mTabTextColorsResId)
        if (mTabTextColorsResId != INVALID_ID) {
            tabTextColors = SkinCompatResources.getColorStateList(
                context,
                mTabTextColorsResId
            )
        }
        mTabSelectedTextColorResId = SkinCompatHelper.checkResourceId(mTabSelectedTextColorResId)
        if (mTabSelectedTextColorResId != INVALID_ID) {
            val selected = SkinCompatResources.getColor(context, mTabSelectedTextColorResId)
            if (tabTextColors != null) {
                tabTextColors?.let { setTabTextColors(it.defaultColor, selected) }
            }
        }
    }

    init {
        val a: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.TabLayout,
            defStyleAttr, 0
        )
        mTabIndicatorColorResId =
            a.getResourceId(R.styleable.TabLayout_tabIndicatorColor, INVALID_ID)
        val tabTextAppearance = a.getResourceId(
            R.styleable.TabLayout_tabTextAppearance,
            R.style.TextAppearance_Design_Tab
        )

        if (a.hasValue(R.styleable.TabLayout_tabTextColor)) {
            // If we have an explicit text color set, use it instead
            mTabTextColorsResId = a.getResourceId(R.styleable.TabLayout_tabTextColor, INVALID_ID)
        }
        if (a.hasValue(R.styleable.TabLayout_tabSelectedTextColor)) {
            // We have an explicit selected text color set, so we need to make merge it with the
            // current colors. This is exposed so that developers can use theme attributes to set
            // this (theme attrs in ColorStateLists are Lollipop+)
            mTabSelectedTextColorResId =
                a.getResourceId(R.styleable.TabLayout_tabSelectedTextColor, INVALID_ID)
        }
        a.recycle()
        applySkin()
    }
}
