package com.zjh.ktwanandroid

import android.app.Application
import androidx.multidex.MultiDex
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.tencent.bugly.Bugly
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import com.zjh.ktwanandroid.app.event.AppViewModel
import com.zjh.ktwanandroid.app.ext.getProcessName
import com.zjh.ktwanandroid.app.skin.SkinMaterialViewInflater
import com.zjh.ktwanandroid.app.widget.loadcallback.EmptyCallback
import com.zjh.ktwanandroid.app.widget.loadcallback.ErrorCallback
import com.zjh.ktwanandroid.app.widget.loadcallback.LoadingCallback
import com.zjh.ktwanandroid.data.datasource.local.DataStore
import com.zjh.ktwanandroid.presentation.error.ErrorActivity
import com.zjh.ktwanandroid.presentation.splash.SplashActivity
import dagger.hilt.android.HiltAndroidApp
import me.hgj.jetpackmvvm.base.BaseApp
import me.hgj.jetpackmvvm.ext.util.jetpackMvvmLog
import me.hgj.jetpackmvvm.ext.util.logd
import skin.support.SkinCompatManager
import skin.support.app.SkinAppCompatViewInflater
import skin.support.app.SkinCardViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.content.res.SkinCompatUserThemeManager


//Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
val appViewModel: AppViewModel by lazy { App.appViewModel }

//Application全局的ViewModel，用于发送全局通知操作
@HiltAndroidApp
class App : BaseApp() {

    companion object {
        lateinit var instance: Application
        lateinit var appViewModel: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
        instance = this
        appViewModel = getAppViewModelProvider()[AppViewModel::class.java]
        MultiDex.install(this)

        initLoadSir()
        initBugly()
        initCaoc()
        initSkip()
    }

    private fun initSkip() {
        //换肤
        SkinCompatManager.withoutActivity(this)
            .addInflater(SkinAppCompatViewInflater()) // 基础控件换肤初始化
            .addInflater(SkinMaterialViewInflater())
            .addInflater(SkinConstraintViewInflater()) // ConstraintLayout 控件换肤初始化[可选]
            .addInflater(SkinCardViewInflater()) // CardView v7 控件换肤初始化[可选]
            .loadSkin()

        val themeColor = DataStore.getThemeColor()
        if(themeColor.isNotEmpty()){
            SkinCompatUserThemeManager.get().addColorState(R.color.colorPrimary,themeColor)
            SkinCompatUserThemeManager.get().addColorState(R.color.colorPrimaryDark,themeColor)
            SkinCompatUserThemeManager.get().addColorState(R.color.colorAccent,themeColor)
        }
    }

    private fun initCaoc() {
        //防止项目崩溃，崩溃后打开错误界面
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
            .enabled(true)//是否启用CustomActivityOnCrash崩溃拦截机制 必须启用！不然集成这个库干啥？？？
            .showErrorDetails(false) //是否必须显示包含错误详细信息的按钮 default: true
            .showRestartButton(false) //是否必须显示“重新启动应用程序”按钮或“关闭应用程序”按钮default: true
            .logErrorOnRestart(false) //是否必须重新堆栈堆栈跟踪 default: true
            .trackActivities(true) //是否必须跟踪用户访问的活动及其生命周期调用 default: false
            .minTimeBetweenCrashesMs(2000) //应用程序崩溃之间必须经过的时间 default: 3000
            .restartActivity(SplashActivity::class.java) // 重启的activity
            .errorActivity(ErrorActivity::class.java) //发生错误跳转的activity
            .apply()
    }

    private fun initBugly() {
        //初始化Bugly
        val context = applicationContext
        // 获取当前包名
        val packageName = context.packageName
        // 获取当前进程名
        val processName = getProcessName(android.os.Process.myPid())
        // 设置是否为上报进程
        val strategy = CrashReport.UserStrategy(context)
        strategy.isUploadProcess = processName == null || processName == packageName
        // 初始化Bugly
        Bugly.init(context, if (BuildConfig.DEBUG) "a52f2b5ebb" else "a52f2b5ebb", BuildConfig.DEBUG)
        "".logd()
        jetpackMvvmLog = BuildConfig.DEBUG
    }

    private fun initLoadSir() {
        //界面加载管理 初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(ErrorCallback())//错误
            .addCallback(EmptyCallback())//空
            .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
            .commit()
    }

}
