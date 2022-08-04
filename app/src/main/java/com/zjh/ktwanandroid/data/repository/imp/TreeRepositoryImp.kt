package com.zjh.ktwanandroid.data.repository.imp

import androidx.paging.PagingData
import com.zjh.ktwanandroid.data.datasource.local.AppDatabase
import com.zjh.ktwanandroid.data.datasource.remote.RemoteDataSource
import com.zjh.ktwanandroid.data.repository.ArticleRepository
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.ArticleHierarchy
import com.zjh.ktwanandroid.domain.model.ArticleNavigation
import com.zjh.ktwanandroid.domain.model.UserInfo
import com.zjh.ktwanandroid.domain.model.enums.ArticleType
import com.zjh.ktwanandroid.domain.repository.MineRepository
import com.zjh.ktwanandroid.domain.repository.TreeRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author zjh
 * 2022/7/20
 */
class TreeRepositoryImp(private val remoteDataSource: RemoteDataSource, private val database: AppDatabase): BaseRepository(), TreeRepository {
    private val articleRepository: ArticleRepository = ArticleRepositoryImp(remoteDataSource,database)
    private val mineRepository: MineRepository = MineRepositoryImp(remoteDataSource,database)

    override fun getSquareArticleList(): Flow<PagingData<Article>> {
        return articleRepository.getArticlePagingData(ArticleType.TREE_SQUARE,0,"")
    }

    override suspend fun addArticle(title: String, link: String): Any? {
        return articleRepository.addArticle(title,link)
    }

    override fun getAskArticleList(): Flow<PagingData<Article>> {
        return articleRepository.getArticlePagingData(ArticleType.TREE_ASK,0,"")
    }

    override suspend fun getHierarchyData(): List<ArticleHierarchy> {
        return exec { remoteDataSource.getHierarchyData() }
    }

    override fun getHierarchyArticleList(cId: Int): Flow<PagingData<Article>> {
        return articleRepository.getArticlePagingData(ArticleType.TREE_HIERARCHY,cId,"")
    }

    override fun getUserInfo(): UserInfo {
        return mineRepository.getUserInfo()
    }

    override suspend fun getArticleNavigationData(): List<ArticleNavigation> {
        return exec { remoteDataSource.getArticleNavigationData() }
    }


    override suspend fun collectArticle(articleId: Int): Any? {
        return articleRepository.collectArticle(articleId)
    }

    override suspend fun unCollectArticle(articleId: Int): Any? {
        return articleRepository.unCollectArticle(articleId)
    }
}