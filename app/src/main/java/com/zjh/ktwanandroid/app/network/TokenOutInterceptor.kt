package com.zjh.ktwanandroid.app.network

import com.google.gson.Gson
import com.zjh.ktwanandroid.data.datasource.remote.model.bean.ApiResponse
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

/**
 * 作者　: hegaojian
 * 时间　: 2022/1/13
 * 描述　: token过期拦截器
 */
class TokenOutInterceptor : Interceptor {

    private val gson: Gson by lazy { Gson() }

    @kotlin.jvm.Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return if (response.body != null && response.body!!.contentType() != null) {
            val mediaType = response.body!!.contentType()
            val string = response.body!!.string()
            val responseBody = ResponseBody.create(mediaType, string)
            val apiResponse = gson.fromJson(string, ApiResponse::class.java)
            //判断逻辑 模拟一下
            if (apiResponse.errorCode == 99999) {
                //TODO 登录过期
            }
            response.newBuilder().body(responseBody).build()
        } else {
            response
        }
    }
}