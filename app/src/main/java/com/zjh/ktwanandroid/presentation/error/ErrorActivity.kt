package com.zjh.ktwanandroid.presentation.error

import StatusBarUtil
import android.content.ClipData
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.blankj.utilcode.util.ToastUtils
import com.zjh.ktwanandroid.app.base.BaseActivity
import com.zjh.ktwanandroid.app.ext.showMessage
import com.zjh.ktwanandroid.app.util.SettingUtil
import com.zjh.ktwanandroid.databinding.ActivityErrorBinding
import init
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.util.clipboardManager
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat

class ErrorActivity : BaseActivity<BaseViewModel,ActivityErrorBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.includeToolbar.toolbar.init("发生错误")
        supportActionBar?.setBackgroundDrawable(ColorDrawable(SettingUtil.getColor(this)))
        StatusBarUtil.setColor(this, SettingUtil.getColor(this), 0)
        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        mDatabind.errorRestart.clickNoRepeat{
            config?.run {
                CustomActivityOnCrash.restartApplication(this@ErrorActivity, this)
            }
        }
        mDatabind.errorSendError.clickNoRepeat {
            CustomActivityOnCrash.getStackTraceFromIntent(intent)?.let {
                showMessage(it,"发现有Bug不去打作者脸？","必须打",{
                    val mClipData = ClipData.newPlainText("errorLog",it)
                    // 将ClipData内容放到系统剪贴板里。
                    clipboardManager?.setPrimaryClip(mClipData)
                    ToastUtils.showShort("已复制错误日志")
                    try {
                        val url = "mqqwpa://im/chat?chat_type=wpa&uin=824868922"
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                        ToastUtils.showShort("请先安装QQ")
                    }
                },"我不敢")
            }
        }
    }

}