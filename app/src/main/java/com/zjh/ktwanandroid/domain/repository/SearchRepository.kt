package com.zjh.ktwanandroid.domain.repository

import androidx.paging.PagingData
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.SearchData
import kotlinx.coroutines.flow.Flow

/**
 * @author zjh
 * 2022/7/25
 */
interface SearchRepository{
    suspend fun getHotTagListData(): List<SearchData>
    suspend fun getHistoryListData(): List<SearchData>
    suspend fun clearHistory()
    suspend fun deleteHistoryData(name: String)
    suspend fun addToHistory(searchData: SearchData)
    suspend fun collectArticle(articleId: Int): Any?
    suspend fun unCollectArticle(articleId: Int): Any?
    fun getArticlePagingData(query: String=""): Flow<PagingData<Article>>
}