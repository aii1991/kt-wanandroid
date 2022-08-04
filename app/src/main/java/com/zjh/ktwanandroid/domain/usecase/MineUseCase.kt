package com.zjh.ktwanandroid.domain.usecase

import com.zjh.ktwanandroid.data.datasource.local.DataStore
import com.zjh.ktwanandroid.domain.model.Integral
import com.zjh.ktwanandroid.domain.model.UserInfo
import com.zjh.ktwanandroid.domain.repository.MineRepository
import me.hgj.jetpackmvvm.exception.ApiException
import javax.inject.Inject

/**
 * @author zjh
 * 2022/6/17
 */
class MineUseCase @Inject constructor(private val mineRepository: MineRepository){
    suspend fun login(userName: String, pwd: String){
        if(userName.isEmpty()){
            throw ApiException.ReqErrException(msg = "用户名不能为空")
        }
        if(pwd.isEmpty()){
            throw ApiException.ReqErrException(msg = "密码不能为空")
        }
        val res = mineRepository.login(userName, pwd)
        DataStore.setUser(res)
    }
    suspend fun register(userName: String, pwd: String,confirmPwd: String){
        if(userName.isEmpty()){
            throw ApiException.ReqErrException(msg = "用户名不能为空")
        }
        if(pwd.isEmpty()){
            throw ApiException.ReqErrException(msg = "密码不能为空")
        }
        if(pwd != confirmPwd){
            throw ApiException.ReqErrException(msg = "两次密码输入不一样")
        }
        mineRepository.register(userName, pwd)
    }

    suspend fun getUserIntegral(): Integral {
        val userIntegral = mineRepository.getUserIntegral()
        val userInfo = mineRepository.getUserInfo()
        userInfo.ranking = userIntegral.rank
        userInfo.integral = userIntegral.coinCount
        mineRepository.saveUserInfo(userInfo)
        return userIntegral
    }

    fun getUserInfo() = mineRepository.getUserInfo()
}