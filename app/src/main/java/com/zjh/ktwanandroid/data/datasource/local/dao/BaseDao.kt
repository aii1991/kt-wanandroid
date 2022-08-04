package com.zjh.ktwanandroid.data.datasource.local.dao

import androidx.room.*

/**
 * @author zjh
 * 2022/5/26
 */

interface BaseDao<Entity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Entity): Long

    @Delete
    fun delete(entity: Entity)

    @Update
    fun update(entity: Entity)
}