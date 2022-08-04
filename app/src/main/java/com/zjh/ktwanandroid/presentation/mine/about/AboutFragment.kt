package com.zjh.ktwanandroid.presentation.mine.about

import android.os.Bundle
import androidx.core.view.isVisible
import com.blankj.utilcode.util.AppUtils
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseFragment
import com.zjh.ktwanandroid.app.ext.showMessage
import com.zjh.ktwanandroid.databinding.FragmentAboutBinding
import com.zjh.ktwanandroid.domain.model.Banner
import com.zjh.ktwanandroid.presentation.webview.WebFragment.ParamKey.BANNER_KEY

import dagger.hilt.android.AndroidEntryPoint
import initClose
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

@AndroidEntryPoint
class AboutFragment : BaseFragment<AboutVm, FragmentAboutBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.includeToolbar.toolbar.initClose(titleStr = "设置") {
            nav().navigateUp()
        }
        mDatabind.llLoginOut.isVisible = mViewModel.isLogin()
        mDatabind.tvTitleSetting.isVisible = mViewModel.isLogin()
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
            nav().navigateAction(R.id.action_aboutFragment_to_webFragment, Bundle()
                .apply { putParcelable(BANNER_KEY, data) })
        }

        mDatabind.tvVersion.text = AppUtils.getAppVersionName()
    }

}