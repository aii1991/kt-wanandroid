package com.zjh.ktwanandroid.domain.repository

import androidx.paging.PagingData
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.CollectUrl
import com.zjh.ktwanandroid.domain.model.Integral
import com.zjh.ktwanandroid.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

/**
 * @author zjh
 * 2022/6/17
 */
interface MineRepository {
    suspend fun login(userName: String, pwd: String): UserInfo
    suspend fun register(userName: String, pwd: String): Any?
    suspend fun getUserIntegral(): Integral
    fun getUserInfo(): UserInfo
    fun saveUserInfo(userInfo: UserInfo)
    fun clearUserInfo()
    fun getMyCollectArticleList(): Flow<PagingData<Article>>
    suspend fun getMyCollectUrlList(): ArrayList<CollectUrl>
    suspend fun collectArticle(articleId: Int): Any?
    suspend fun unCollectArticle(articleId: Int): Any?
    suspend fun deleteArticle(article:Article)
}