package com.zjh.ktwanandroid.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zjh.ktwanandroid.data.datasource.local.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(remoteKey: List<RemoteKeyEntity>)

  @Query("SELECT * FROM t_remote_keys WHERE articleId = :articleId AND articleType = :articleType AND categoryId = :categoryId")
  suspend fun remoteKeysArticleId(articleId: Int, articleType:Int, categoryId:Int): RemoteKeyEntity

  @Query("DELETE FROM t_remote_keys WHERE articleType = :articleType AND categoryId = :categoryId")
  suspend fun clearRemoteKeys(articleType: Int, categoryId:Int)
}