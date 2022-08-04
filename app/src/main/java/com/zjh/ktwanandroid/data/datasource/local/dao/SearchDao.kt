package com.zjh.ktwanandroid.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zjh.ktwanandroid.data.datasource.local.entity.RemoteKeyEntity
import com.zjh.ktwanandroid.data.datasource.local.entity.SearchEntity

/**
 * @author zjh
 * 2022/7/25
 */
@Dao
interface SearchDao : BaseDao<SearchEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyEntity>)

    @Query("SELECT * FROM t_search")
    fun findAll(): List<SearchEntity>

    @Query("DELETE FROM t_search WHERE 1 = 1")
    suspend fun clear()

    @Query("DELETE FROM t_search WHERE name = :name")
    suspend fun deleteByName(name: String)
}