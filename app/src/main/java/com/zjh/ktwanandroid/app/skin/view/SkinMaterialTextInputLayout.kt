package com.zjh.ktwanandroid.app.skin.view

import com.google.android.material.textfield.TextInputLayout
import android.content.res.ColorStateList
import skin.support.content.res.SkinCompatResources
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatEditText
import android.content.Context
import android.widget.TextView
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.StyleRes
import androidx.customview.widget.ExploreByTouchHelper.INVALID_ID
import com.google.android.material.R
import skin.support.widget.SkinCompatBackgroundHelper
import skin.support.widget.SkinCompatSupportable
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.Method



/**
 * @author zjh
 * 2022/8/8
 */
class SkinMaterialTextInputLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    TextInputLayout(context, attrs, defStyleAttr), SkinCompatSupportable {
    private val mBackgroundTintHelper: SkinCompatBackgroundHelper?
    private var mPasswordToggleResId: Int = INVALID_ID
    private var mCounterTextColorResId: Int = INVALID_ID
    private var mErrorTextColorResId: Int = INVALID_ID
    private var mFocusedTextColorResId: Int = INVALID_ID
    private var mDefaultTextColorResId: Int = INVALID_ID

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    private fun loadCounterTextColorResFromAttributes(@StyleRes resId: Int) {
        if (resId != INVALID_ID) {
            val counterTA =
                context.obtainStyledAttributes(resId, skin.support.R.styleable.SkinTextAppearance)
            if (counterTA.hasValue(skin.support.R.styleable.SkinTextAppearance_android_textColor)) {
                mCounterTextColorResId = counterTA.getResourceId(
                    skin.support.R.styleable.SkinTextAppearance_android_textColor,
                    INVALID_ID
                )
            }
            counterTA.recycle()
        }
        applyCounterTextColorResource()
    }

    override fun setCounterEnabled(enabled: Boolean) {
        super.setCounterEnabled(enabled)
        if (enabled) {
            applyCounterTextColorResource()
        }
    }

    private fun applyCounterTextColorResource() {
        mCounterTextColorResId = SkinCompatHelper.checkResourceId(mCounterTextColorResId)
        if (mCounterTextColorResId != INVALID_ID) {
            val counterView = getCounterView()
            if (counterView != null) {
                counterView.setTextColor(
                    SkinCompatResources.getColor(
                        context,
                        mCounterTextColorResId
                    )
                )
                updateEditTextBackgroundInternal()
            }
        }
    }

    private fun getCounterView(): TextView? {
        try {
            val counterView: Field = TextInputLayout::class.java.getDeclaredField("mCounterView")
            counterView.isAccessible = true
            return counterView.get(this) as TextView
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun setErrorTextAppearance(@StyleRes resId: Int) {
        super.setErrorTextAppearance(resId)
        loadErrorTextColorResFromAttributes(resId)
    }

    private fun loadErrorTextColorResFromAttributes(@StyleRes resId: Int) {
        if (resId != INVALID_ID) {
            val errorTA =
                context.obtainStyledAttributes(resId, skin.support.R.styleable.SkinTextAppearance)
            if (errorTA.hasValue(skin.support.R.styleable.SkinTextAppearance_android_textColor)) {
                mErrorTextColorResId = errorTA.getResourceId(
                    skin.support.R.styleable.SkinTextAppearance_android_textColor,
                    INVALID_ID
                )
            }
            errorTA.recycle()
        }
        applyErrorTextColorResource()
    }

    override fun setErrorEnabled(enabled: Boolean) {
        super.setErrorEnabled(enabled)
        if (enabled) {
            applyErrorTextColorResource()
        }
    }

    private fun applyErrorTextColorResource() {
        mErrorTextColorResId = SkinCompatHelper.checkResourceId(mErrorTextColorResId)
        if (mErrorTextColorResId != INVALID_ID && mErrorTextColorResId != R.color.design_error) {
            val errorView = getErrorView()
            if (errorView != null) {
                errorView.setTextColor(SkinCompatResources.getColor(context, mErrorTextColorResId))
                updateEditTextBackgroundInternal()
            }
        }
    }

    private fun getErrorView(): TextView? {
        try {
            val errorView: Field = TextInputLayout::class.java.getDeclaredField("mErrorView")
            errorView.isAccessible = true
            return errorView.get(this) as TextView
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun updateEditTextBackgroundInternal() {
        try {
            val updateEditTextBackground: Method =
                TextInputLayout::class.java.getDeclaredMethod("updateEditTextBackground")
            updateEditTextBackground.isAccessible = true
            updateEditTextBackground.invoke(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setDefaultTextColor(colors: ColorStateList) {
        try {
            val defaultTextColor: Field =
                TextInputLayout::class.java.getDeclaredField("mDefaultTextColor")
            defaultTextColor.isAccessible = true
            defaultTextColor.set(this, colors)
            updateLabelState()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun applyFocusedTextColorResource() {
        mFocusedTextColorResId = SkinCompatHelper.checkResourceId(mFocusedTextColorResId)
        if (mFocusedTextColorResId != INVALID_ID && mFocusedTextColorResId != R.color.abc_hint_foreground_material_light) {
            setFocusedTextColor(
                SkinCompatResources.getColorStateList(
                    context,
                    mFocusedTextColorResId
                )
            )
        } else if (editText != null) {
            var textColorResId: Int = INVALID_ID
            if (editText is SkinCompatEditText) {
                textColorResId = (editText as SkinCompatEditText?)!!.textColorResId
            } else if (editText is SkinMaterialTextInputEditText) {
                textColorResId = (editText as SkinMaterialTextInputEditText).getTextColorResId()
            }
            textColorResId = SkinCompatHelper.checkResourceId(textColorResId)
            if (textColorResId != INVALID_ID) {
                val colors = SkinCompatResources.getColorStateList(context, textColorResId)
                setFocusedTextColor(colors)
            }
        }
    }

    private fun setFocusedTextColor(colors: ColorStateList) {
        try {
            val focusedTextColor: Field =
                TextInputLayout::class.java.getDeclaredField("mFocusedTextColor")
            focusedTextColor.isAccessible = true
            focusedTextColor.set(this, colors)
            updateLabelState()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateLabelState() {
        try {
            val updateLabelState: Method =
                TextInputLayout::class.java.getDeclaredMethod(
                    "updateLabelState",
                    Boolean::class.javaPrimitiveType
                )
            updateLabelState.setAccessible(true)
            updateLabelState.invoke(this, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun applySkin() {
        applyErrorTextColorResource()
        applyCounterTextColorResource()
        applyFocusedTextColorResource()
        mBackgroundTintHelper?.applySkin()
    }

    init {
        mBackgroundTintHelper = SkinCompatBackgroundHelper(this)
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr)
        val a: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.TextInputLayout,
            defStyleAttr,
            R.style.Widget_Design_TextInputLayout
        )
        if (a.hasValue(R.styleable.TextInputLayout_android_textColorHint)) {
            mFocusedTextColorResId =
                a.getResourceId(R.styleable.TextInputLayout_android_textColorHint, INVALID_ID)
            mDefaultTextColorResId = mFocusedTextColorResId
            applyFocusedTextColorResource()
        }
        val errorTextAppearance =
            a.getResourceId(R.styleable.TextInputLayout_errorTextAppearance, INVALID_ID)
        loadErrorTextColorResFromAttributes(errorTextAppearance)
        val counterTextAppearance =
            a.getResourceId(R.styleable.TextInputLayout_counterTextAppearance, INVALID_ID)
        loadCounterTextColorResFromAttributes(counterTextAppearance)
        mPasswordToggleResId =
            a.getResourceId(R.styleable.TextInputLayout_passwordToggleDrawable, INVALID_ID)
        a.recycle()
    }
}
