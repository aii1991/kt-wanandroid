package com.zjh.ktwanandroid.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author zjh
 * 2022/7/25
 */
@Entity(tableName = "t_search", indices = [Index(value=["name"], unique = true)])
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "")
