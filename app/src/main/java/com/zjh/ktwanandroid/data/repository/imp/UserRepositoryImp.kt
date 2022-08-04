package com.zjh.ktwanandroid.data.repository.imp

import com.zjh.ktwanandroid.data.datasource.remote.RemoteDataSource
import com.zjh.ktwanandroid.data.repository.UserRepository
import com.zjh.ktwanandroid.domain.model.Integral
import com.zjh.ktwanandroid.domain.model.UserInfo

/**
 * @author zjh
 * 2022/6/17
 */
class UserRepositoryImp(private val remoteDataSource: RemoteDataSource): BaseRepository(), UserRepository{

    override suspend fun login(userName: String, pwd: String): UserInfo {
        return exec { remoteDataSource.login(userName, pwd) }
    }

    override suspend fun register(userName: String, pwd: String): Any? {
        return exec { remoteDataSource.register(userName, pwd, pwd) }
    }

    override suspend fun getUserIntegral(): Integral {
        return exec { remoteDataSource.getIntegral() }
    }


}