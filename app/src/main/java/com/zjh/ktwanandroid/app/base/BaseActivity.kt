package com.zjh.ktwanandroid.app.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.SkinAppCompatDelegateImpl
import androidx.viewbinding.ViewBinding
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.ext.isTranslucentOrFloating
import com.zjh.ktwanandroid.app.ext.setActivityOrientation
import com.zjh.ktwanandroid.data.datasource.local.DataStore
import kotlinx.coroutines.CoroutineScope
import me.hgj.jetpackmvvm.base.activity.BaseVmDbActivity
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import skin.support.content.res.SkinCompatResources
import skin.support.widget.SkinCompatSupportable


/**
 * @author zjh
 * 2022/5/12
 */
abstract class BaseActivity<VM: BaseViewModel, DB: ViewBinding> : BaseVmDbActivity<VM, DB>(),
    SkinCompatSupportable {

    override fun onCreate(savedInstanceState: Bundle?) {
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.O && this.isTranslucentOrFloating()){
            this.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
        }
        super.onCreate(savedInstanceState)

    }

    override fun applySkin() {

        window.statusBarColor = SkinCompatResources.getColor(this,R.color.colorPrimaryDark)
    }

    override fun getDelegate(): AppCompatDelegate {
        return SkinAppCompatDelegateImpl.get(this, this);
    }

    /**
     * 创建liveData观察者
     */
    override suspend fun observeUiState(coroutineScope: CoroutineScope) {}

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {}

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {}
}