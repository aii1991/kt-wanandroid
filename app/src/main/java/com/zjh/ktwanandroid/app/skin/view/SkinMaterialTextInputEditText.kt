package com.zjh.ktwanandroid.app.skin.view

import android.R
import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.customview.widget.ExploreByTouchHelper.INVALID_ID
import com.google.android.material.textfield.TextInputEditText
import skin.support.widget.SkinCompatBackgroundHelper
import skin.support.widget.SkinCompatSupportable
import skin.support.widget.SkinCompatTextHelper


/**
 * @author zjh
 * 2022/8/8
 */
class SkinMaterialTextInputEditText(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    TextInputEditText(context, attrs, defStyleAttr), SkinCompatSupportable {
    private val mTextHelper: SkinCompatTextHelper?
    private val mBackgroundTintHelper: SkinCompatBackgroundHelper?

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.editTextStyle
    ) {
    }

    override fun setBackgroundResource(@DrawableRes resId: Int) {
        super.setBackgroundResource(resId)
        mBackgroundTintHelper?.onSetBackgroundResource(resId)
    }

    override fun setTextAppearance(resId: Int) {
        setTextAppearance(context, resId)
    }

    override fun setTextAppearance(context: Context, resId: Int) {
        super.setTextAppearance(context, resId)
        mTextHelper?.onSetTextAppearance(context, resId)
    }

    fun getTextColorResId(): Int {
        return mTextHelper?.textColorResId ?: INVALID_ID
    }

    override fun setCompoundDrawablesRelativeWithIntrinsicBounds(
        @DrawableRes start: Int,
        @DrawableRes top: Int,
        @DrawableRes end: Int,
        @DrawableRes bottom: Int
    ) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
        mTextHelper?.onSetCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        @DrawableRes left: Int,
        @DrawableRes top: Int,
        @DrawableRes right: Int,
        @DrawableRes bottom: Int
    ) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
        mTextHelper?.onSetCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    }

    override fun applySkin() {
        mBackgroundTintHelper?.applySkin()
        mTextHelper?.applySkin()
    }

    init {
        mBackgroundTintHelper = SkinCompatBackgroundHelper(this)
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr)
        mTextHelper = SkinCompatTextHelper.create(this)
        mTextHelper.loadFromAttributes(attrs, defStyleAttr)
    }
}
