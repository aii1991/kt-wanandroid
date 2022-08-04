package com.zjh.ktwanandroid.domain.repository

import androidx.paging.PagingData
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.ArticleCategory
import kotlinx.coroutines.flow.Flow

/**
 * @author zjh
 * 2022/7/15
 */
interface PublicNumberRepository {
    /**
     * 获取公众号分类列表
     */
    suspend fun getArticleCategories(): List<ArticleCategory>
    /**
     * 根据分类Id获取公众号相关文章列表数据
     * @param cId 分类id
     */
    fun getArticleByCategoryId(cId: Int): Flow<PagingData<Article>>

    suspend fun collectArticle(articleId: Int): Any?
    suspend fun unCollectArticle(articleId: Int): Any?
}