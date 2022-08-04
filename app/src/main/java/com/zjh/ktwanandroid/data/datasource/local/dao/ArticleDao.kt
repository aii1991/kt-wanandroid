package com.zjh.ktwanandroid.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zjh.ktwanandroid.data.datasource.local.entity.ArticleEntity

/**
 * @author zjh
 * 2022/5/31
 */
@Dao
interface ArticleDao : BaseDao<ArticleEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<ArticleEntity>)

    @Query("SELECT * FROM t_article WHERE articleType = :articleType AND categoryId = :cId ORDER BY publishTime DESC")
    fun pagingSource(articleType: Int,cId: Int): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM t_article WHERE articleType = :articleType AND categoryId = :cId")
    suspend fun clearArticle(articleType: Int,cId: Int)

    @Query("UPDATE t_article SET collect = :collect WHERE id = :articleId")
    suspend fun updateCollectState(articleId:Int, collect: Int)
}