package com.zjh.ktwanandroid.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author zjh
 * 2022/5/26
 */
@Entity(tableName = "t_user_info")
data class UserInfoEntity(@PrimaryKey var id: Long,
                          var email: String="",
                          var icon: String="",
                          var nickname: String="",
                          var type: Int =0,
                          var username: String="")