package com.zjh.ktwanandroid.data.repository.imp

import com.zjh.ktwanandroid.data.datasource.remote.model.bean.ApiResponse
import me.hgj.jetpackmvvm.exception.ApiException

/**
 * @author zjh
 * 2022/8/4
 */
open class BaseRepository {
    suspend fun <T> exec(block:suspend ()-> ApiResponse<T>): T{
        val res = block()
        if(res.isSucces()){
            return res.data
        }else{
            throw ApiException.ReqErrException(res.errorCode,res.errorMsg)
        }
    }
}