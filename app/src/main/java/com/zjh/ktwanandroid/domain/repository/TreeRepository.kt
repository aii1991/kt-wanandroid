package com.zjh.ktwanandroid.domain.repository

import androidx.paging.PagingData
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.ArticleHierarchy
import com.zjh.ktwanandroid.domain.model.ArticleNavigation
import com.zjh.ktwanandroid.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

/**
 * @author zjh
 * 2022/7/19
 */
interface TreeRepository {
    /**
     * 获取广场文章列表
     */
    fun getSquareArticleList(): Flow<PagingData<Article>>

    /**
     * 添加文章
     */
    suspend fun addArticle(title:String, link:String): Any?

    /**
     * 获取每日一问文章列表
     */
    fun getAskArticleList(): Flow<PagingData<Article>>

    /**
     * 获取系统体系数据
     */
    suspend fun getHierarchyData(): List<ArticleHierarchy>

    /**
     * 知识体系下的文章数据
     */
    fun getHierarchyArticleList(cId:Int): Flow<PagingData<Article>>

    fun getUserInfo():UserInfo

    /**
     * 获取文章导航数据
     */
    suspend fun getArticleNavigationData(): List<ArticleNavigation>

    suspend fun collectArticle(articleId: Int): Any?
    suspend fun unCollectArticle(articleId: Int): Any?
}