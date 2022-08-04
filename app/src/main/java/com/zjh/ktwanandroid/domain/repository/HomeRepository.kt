package com.zjh.ktwanandroid.domain.repository

import androidx.paging.PagingData
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.Banner
import kotlinx.coroutines.flow.Flow

/**
 * @author zjh
 * 2022/5/26
 */
interface HomeRepository {
    fun getArticlePagingData(query: String = ""): Flow<PagingData<Article>>
    suspend fun getBanner(): List<Banner>
    suspend fun collectArticle(articleId: Int): Any?
    suspend fun unCollectArticle(articleId: Int): Any?
}