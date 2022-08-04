package com.zjh.ktwanandroid.data.repository.imp

import androidx.paging.PagingData
import com.zjh.ktwanandroid.data.datasource.local.AppDatabase
import com.zjh.ktwanandroid.data.datasource.remote.RemoteDataSource
import com.zjh.ktwanandroid.data.repository.ArticleRepository
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.Banner
import com.zjh.ktwanandroid.domain.model.enums.ArticleType
import com.zjh.ktwanandroid.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author zjh
 * 2022/5/26
 */
class HomeRepositoryImp(remoteDataSource: RemoteDataSource, database: AppDatabase):  BaseRepository(),HomeRepository{
    private val articleRepository: ArticleRepository = ArticleRepositoryImp(remoteDataSource,database)

    override fun getArticlePagingData(query: String): Flow<PagingData<Article>> {
        return articleRepository.getArticlePagingData(ArticleType.HOME,0,query)
    }

    override suspend fun getBanner(): List<Banner> {
        return articleRepository.getBanner()
    }

    override suspend fun collectArticle(articleId: Int): Any? {
        return articleRepository.collectArticle(articleId)
    }

    override suspend fun unCollectArticle(articleId: Int): Any? {
        return articleRepository.unCollectArticle(articleId)
    }

}