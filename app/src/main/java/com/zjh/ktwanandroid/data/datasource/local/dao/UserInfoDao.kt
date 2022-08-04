package com.zjh.ktwanandroid.data.datasource.local.dao

import androidx.room.*
import com.zjh.ktwanandroid.data.datasource.local.entity.UserInfoEntity

/**
 * @author zjh
 * 2022/5/26
 */
@Dao
interface UserInfoDao : BaseDao<UserInfoEntity>{
    @Query("SELECT * FROM t_user_info WHERE id = :id")
    fun getUserInfoById(id: Long): UserInfoEntity?
}