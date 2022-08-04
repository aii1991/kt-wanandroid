package com.zjh.ktwanandroid.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.blankj.utilcode.util.ToastUtils
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseActivity
import com.zjh.ktwanandroid.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.network.manager.NetState

@AndroidEntryPoint
class MainActivity : BaseActivity<BaseViewModel,ActivityMainBinding>() {
    companion object {
        @JvmStatic
        fun startActivity(context:Context, clazz:Class<*>){
           context.startActivity(Intent(context,clazz))
        }
    }

    var exitTime = 0L
    override fun initView(savedInstanceState: Bundle?) {
        onBackPressedDispatcher.addCallback(this,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity,R.id.host_fragment)
                if(nav.currentDestination != null && nav.currentDestination!!.id != R.id.main_fragment){
                    nav.navigateUp()
                }else{
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtils.showShort("再按一次退出程序")
                        exitTime = System.currentTimeMillis()
                    } else {
                        finish()
                    }
                }
            }
        })
    }

    /**
     * 示例，在Activity/Fragment中如果想监听网络变化，可重写onNetworkStateChanged该方法
     */
    override fun onNetworkStateChanged(netState: NetState) {
        super.onNetworkStateChanged(netState)
        if (netState.isSuccess) {
            Toast.makeText(applicationContext, "我特么终于有网了啊!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "我特么怎么断网了!", Toast.LENGTH_SHORT).show()
        }
    }
}