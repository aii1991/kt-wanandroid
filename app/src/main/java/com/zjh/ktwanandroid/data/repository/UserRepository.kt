package com.zjh.ktwanandroid.data.repository

import com.zjh.ktwanandroid.domain.model.Integral
import com.zjh.ktwanandroid.domain.model.UserInfo

/**
 * @author zjh
 * 2022/6/17
 */
interface UserRepository {
    suspend fun login(userName: String, pwd: String): UserInfo
    suspend fun register(userName: String, pwd: String): Any?
    suspend fun getUserIntegral(): Integral
}