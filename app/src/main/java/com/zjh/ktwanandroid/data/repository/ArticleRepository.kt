package com.zjh.ktwanandroid.data.repository

import androidx.paging.PagingData
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.ArticleCategory
import com.zjh.ktwanandroid.domain.model.Banner
import com.zjh.ktwanandroid.domain.model.enums.ArticleType
import kotlinx.coroutines.flow.Flow

/**
 * @author zjh
 * 2022/6/17
 */
interface ArticleRepository {
    fun getArticlePagingData(articleType: ArticleType, categoryId:Int = 0, query:String = ""): Flow<PagingData<Article>>
    suspend fun getBanner(): List<Banner>
    suspend fun collectArticle(articleId: Int): Any?
    suspend fun unCollectArticle(articleId: Int): Any?
    suspend fun getProjectArticleCategories(): List<ArticleCategory>
    suspend fun getPublicNumberArticleCategories(): List<ArticleCategory>
    suspend fun addArticle(title:String, link:String): Any?
    suspend fun deleteArticle(article:Article)
}