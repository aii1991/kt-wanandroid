package com.zjh.ktwanandroid.app.skin.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout
import skin.support.widget.SkinCompatBackgroundHelper
import skin.support.widget.SkinCompatSupportable


/**
 * @author zjh
 * 2022/8/8
 */
class SkinMaterialAppBarLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : AppBarLayout(context, attrs, defStyleAttr), SkinCompatSupportable{
    constructor(context: Context) : this(context,null,0){}
    constructor(context: Context,attrs: AttributeSet?) : this(context,attrs,0){}

    private val mBackgroundTintHelper: SkinCompatBackgroundHelper?

    init {
        mBackgroundTintHelper = SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, 0);
    }

    override fun applySkin() {
        mBackgroundTintHelper?.applySkin()
    }
}