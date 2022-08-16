package com.zjh.ktwanandroid.data.datasource.local

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import com.zjh.ktwanandroid.domain.model.UserInfo

/**
 * @author zjh
 * 2022/6/17
 */
object DataStore {
    /**
     * 获取保存的账户信息
     */
    fun getUser(): UserInfo? {
        val kv = MMKV.mmkvWithID("app")
        val userStr = kv.decodeString("user")
        return if (TextUtils.isEmpty(userStr)) {
            null
        } else {
            Gson().fromJson(userStr, UserInfo::class.java)
        }
    }

    /**
     * 设置账户信息
     */
    fun setUser(userResponse: UserInfo?) {
        val kv = MMKV.mmkvWithID("app")
        if (userResponse == null) {
            kv.encode("user", "")
            setIsLogin(false)
        } else {
            kv.encode("user", Gson().toJson(userResponse))
            setIsLogin(true)
        }

    }

    /**
     * 是否已经登录
     */
    fun isLogin(): Boolean {
        val kv = MMKV.mmkvWithID("app")
        return kv.decodeBool("login", false)
    }

    /**
     * 设置是否已经登录
     */
    private fun setIsLogin(isLogin: Boolean) {
        val kv = MMKV.mmkvWithID("app")
        kv.encode("login", isLogin)
    }

    fun setThemeColor(color:String){
        val kv = MMKV.mmkvWithID("app")
        kv.encode("theme_color",color)
    }

    fun getThemeColor():String{
        val kv = MMKV.mmkvWithID("app")
        return kv.decodeString("theme_color","")
    }
}