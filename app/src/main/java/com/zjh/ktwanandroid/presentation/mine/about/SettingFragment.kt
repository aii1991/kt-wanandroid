package com.zjh.ktwanandroid.presentation.mine.about

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.blankj.utilcode.util.AppUtils
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseFragment
import com.zjh.ktwanandroid.app.ext.showMessage
import com.zjh.ktwanandroid.app.util.ColorUtil
import com.zjh.ktwanandroid.databinding.FragmentSettingBinding
import com.zjh.ktwanandroid.domain.model.Banner
import com.zjh.ktwanandroid.presentation.webview.WebFragment.ParamKey.BANNER_KEY

import dagger.hilt.android.AndroidEntryPoint
import initClose
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import skin.support.SkinCompatManager
import skin.support.content.res.ColorState
import skin.support.content.res.SkinCompatUserThemeManager

@AndroidEntryPoint
class SettingFragment : BaseFragment<SettingVm, FragmentSettingBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.includeToolbar.toolbar.initClose(titleStr = "设置") {
            nav().navigateUp()
        }

        val defaultColor = Color.parseColor(mViewModel.getThemeColor(requireContext()))
        mDatabind.colorView.color = defaultColor
        mDatabind.colorView.border = defaultColor
        mDatabind.llLoginOut.isVisible = mViewModel.isLogin()
        mDatabind.llThemeColor.setOnClickListener {
            MaterialColorPickerDialog.Builder(requireActivity())
                .setTitle("选择颜色")
                .setColorRes(ColorUtil.ACCENT_COLORS)
                .setColorShape(ColorShape.CIRCLE)
                .setColorSwatch(ColorSwatch.A300)
                .setDefaultColor(R.color.colorPrimary)
                .setColorListener{color, colorHex ->
                    mDatabind.colorView.color = color
                    mDatabind.colorView.border = color
                    mViewModel.setThemeColor(colorHex)
                    updateThemeColor(colorHex)
                }
                .show()
        }
        mDatabind.llLoginOut.setOnClickListener {
            showMessage( "确定退出登录吗",
                positiveButtonText = "退出",
                negativeButtonText = "取消",
                positiveAction = {
                    //清空cookie
                    mViewModel.loginOut()
                    nav().navigateUp()
                })
        }

        mDatabind.llProjectCode.setOnClickListener {
            val data = Banner(
                title = mDatabind.tvDesc.text.toString(),
                url = mDatabind.tvGithub.text.toString()
            )
            nav().navigateAction(R.id.action_settingFragment_to_webFragment, Bundle()
                .apply { putParcelable(BANNER_KEY, data) })
        }

        mDatabind.tvVersion.text = AppUtils.getAppVersionName()
    }

    private fun updateThemeColor(colorHex:String) {
        SkinCompatUserThemeManager.get().addColorState(R.color.colorPrimary,colorHex)
        SkinCompatUserThemeManager.get().addColorState(R.color.colorPrimaryDark,colorHex)
        SkinCompatUserThemeManager.get().addColorState(R.color.colorAccent,colorHex)
        SkinCompatManager.getInstance().notifyUpdateSkin()
    }

}