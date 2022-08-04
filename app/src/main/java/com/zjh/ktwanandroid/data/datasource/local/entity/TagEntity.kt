package com.zjh.ktwanandroid.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author zjh
 * 2022/5/31
 */
@Entity(tableName = "t_tag")
data class TagEntity(@PrimaryKey var name:String, var url:String)
