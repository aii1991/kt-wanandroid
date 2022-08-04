package com.zjh.ktwanandroid.presentation.splash

import android.animation.Animator
import android.os.Bundle
import com.zjh.ktwanandroid.app.base.BaseActivity
import com.zjh.ktwanandroid.app.widget.openingstartanimation.OpeningStartAnimation
import com.zjh.ktwanandroid.databinding.ActivitySplashBinding
import com.zjh.ktwanandroid.presentation.main.MainActivity
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.util.LogUtils

class SplashActivity() : BaseActivity<BaseViewModel, ActivitySplashBinding>(),
    Animator.AnimatorListener {
    override fun initView(savedInstanceState: Bundle?) {
        val openingStartAnimation = OpeningStartAnimation.Builder(this)
            .setAppStatement("kt wan android")
            .setAnimationFinishTime(500)
            .setAnimationListener(this)
            .create()

        openingStartAnimation.show(this)
    }

    override fun onAnimationStart(p0: Animator?) {

    }

    override fun onAnimationEnd(p0: Animator?) {
        LogUtils.debugInfo("animation end")
        MainActivity.startActivity(this@SplashActivity, MainActivity::class.java)
        finish()
    }

    override fun onAnimationCancel(p0: Animator?) {

    }

    override fun onAnimationRepeat(p0: Animator?) {

    }
}