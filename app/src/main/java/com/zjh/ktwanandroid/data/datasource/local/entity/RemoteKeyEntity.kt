package com.zjh.ktwanandroid.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author zjh
 * 2022/5/31
 */
@Entity(tableName = "t_remote_keys", primaryKeys = ["articleId","articleType","categoryId"])
data class RemoteKeyEntity(
                           val articleId: Int,
                           val articleType: Int,
                           val categoryId: Int,
                           val prevKey: Int?,
                           val nextKey: Int?)