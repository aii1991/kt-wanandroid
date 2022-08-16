package com.zjh.ktwanandroid.app.skin.view

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import skin.support.widget.SkinCompatBackgroundHelper
import skin.support.widget.SkinCompatSupportable


/**
 * @author zjh
 * 2022/8/8
 */
class SkinMaterialCoordinatorLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    CoordinatorLayout(context, attrs, defStyleAttr), SkinCompatSupportable {
    private val mBackgroundTintHelper: SkinCompatBackgroundHelper?

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun applySkin() {
        mBackgroundTintHelper?.applySkin()
    }

    init {
        mBackgroundTintHelper = SkinCompatBackgroundHelper(this)
        mBackgroundTintHelper.loadFromAttributes(attrs, 0)
    }
}