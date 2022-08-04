package com.zjh.ktwanandroid.app.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.zjh.ktwanandroid.app.ext.isTranslucentOrFloating
import com.zjh.ktwanandroid.app.ext.setActivityOrientation
import kotlinx.coroutines.CoroutineScope
import me.hgj.jetpackmvvm.base.activity.BaseVmDbActivity
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @author zjh
 * 2022/5/12
 */
abstract class BaseActivity<VM: BaseViewModel, DB: ViewBinding> : BaseVmDbActivity<VM, DB>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.O && this.isTranslucentOrFloating()){
            this.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
        }
        super.onCreate(savedInstanceState)

    }

    /**
     * 创建liveData观察者
     */
    override suspend fun observeUiState(coroutineScope: CoroutineScope) {}

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
//        showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
//        dismissLoadingExt()
    }
}