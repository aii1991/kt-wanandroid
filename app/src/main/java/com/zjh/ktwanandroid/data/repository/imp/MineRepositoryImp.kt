package com.zjh.ktwanandroid.data.repository.imp

import androidx.paging.PagingData
import com.zjh.ktwanandroid.data.datasource.local.AppDatabase
import com.zjh.ktwanandroid.data.datasource.local.DataStore
import com.zjh.ktwanandroid.data.datasource.remote.RemoteDataSource
import com.zjh.ktwanandroid.data.repository.ArticleRepository
import com.zjh.ktwanandroid.data.repository.UserRepository
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.CollectUrl
import com.zjh.ktwanandroid.domain.model.Integral
import com.zjh.ktwanandroid.domain.model.UserInfo
import com.zjh.ktwanandroid.domain.model.enums.ArticleType
import com.zjh.ktwanandroid.domain.repository.MineRepository
import kotlinx.coroutines.flow.Flow
import me.hgj.jetpackmvvm.exception.ApiException

/**
 * @author zjh
 * 2022/6/17
 */
class MineRepositoryImp(private val remoteDataSource: RemoteDataSource, database: AppDatabase) : BaseRepository(), MineRepository{
    private val userRepository: UserRepository = UserRepositoryImp(remoteDataSource)
    private val articleRepository: ArticleRepository = ArticleRepositoryImp(remoteDataSource,database)

    override suspend fun login(userName: String, pwd: String): UserInfo {
        return userRepository.login(userName, pwd)
    }

    override suspend fun register(userName: String, pwd: String): Any? {
        return userRepository.register(userName, pwd)
    }

    override suspend fun getUserIntegral(): Integral {
        return userRepository.getUserIntegral()
    }

    override fun getUserInfo(): UserInfo {
        return DataStore.getUser() ?: throw ApiException.NotLoginException()
    }

    override fun saveUserInfo(userInfo: UserInfo) {
        DataStore.setUser(userInfo)
    }

    override fun clearUserInfo() {
        DataStore.setUser(null)
    }

    override fun getMyCollectArticleList(): Flow<PagingData<Article>> {
       return articleRepository.getArticlePagingData(ArticleType.MY_COLLECT)
    }

    override suspend fun getMyCollectUrlList(): ArrayList<CollectUrl> {
        return exec { remoteDataSource.getMyCollectUrlList() }
    }

    override suspend fun collectArticle(articleId: Int): Any? {
        return articleRepository.collectArticle(articleId)
    }

    override suspend fun unCollectArticle(articleId: Int): Any? {
        return articleRepository.unCollectArticle(articleId)
    }

    override suspend fun deleteArticle(article: Article) {
        articleRepository.deleteArticle(article)
    }
}