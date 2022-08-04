package com.zjh.ktwanandroid.app.base

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.blankj.utilcode.util.ToastUtils
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.util.SettingUtil
import hideSoftKeyboard
import me.hgj.jetpackmvvm.base.fragment.BaseVmDbFragment
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/21
 * 描述　: 你项目中的Fragment基类，在这里实现显示弹窗，吐司，还有自己的需求操作
 *
 */
abstract class BaseFragment<VM : BaseViewModel, DB : ViewBinding> : BaseVmDbFragment<VM, DB>() {
    private var loadingDialog: MaterialDialog? = null

    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法
     */
    override fun lazyLoadData() {}

    /**
     * Fragment执行onViewCreated后触发
     */
    override fun initData() {

    }

    fun showErrMsgToUser(msg: String){
        showToast(msg)
    }

    fun showToast(msg: String) {
        if (msg.isNotEmpty()) {
            ToastUtils.showLong(msg)
        }
    }

    fun showLoading(message: String,isShow:Boolean=false){
        if(isShow){
            showLoading(message)
        }else{
            dismissLoading()
        }
    }

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        if (!this.isDetached) {
            if (loadingDialog == null) {
                loadingDialog = MaterialDialog(requireContext())
                    .cancelable(true)
                    .cancelOnTouchOutside(false)
                    .cornerRadius(12f)
                    .customView(R.layout.layout_custom_progress_dialog_view)
                    .lifecycleOwner(this)
                loadingDialog?.getCustomView()?.run {
                    this.findViewById<TextView>(R.id.loading_tips).text = message
                    this.findViewById<ProgressBar>(R.id.progressBar).indeterminateTintList =
                        SettingUtil.getOneColorStateList(context)
                }
            }
            loadingDialog?.show()
        }
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(activity)
    }

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    override fun lazyLoadTime(): Long {
        return 300
    }
}