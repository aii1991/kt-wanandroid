package com.zjh.ktwanandroid.data.repository.imp

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.zjh.ktwanandroid.AppCfg
import com.zjh.ktwanandroid.data.datasource.local.AppDatabase
import com.zjh.ktwanandroid.data.datasource.remote.RemoteDataSource
import com.zjh.ktwanandroid.data.mapper.toArticle
import com.zjh.ktwanandroid.data.mapper.toEntity
import com.zjh.ktwanandroid.data.pagging.mediator.ArticleRemoteMediator
import com.zjh.ktwanandroid.data.repository.ArticleRepository
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.ArticleCategory
import com.zjh.ktwanandroid.domain.model.enums.ArticleType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author zjh
 * 2022/6/17
 */
class ArticleRepositoryImp(private val remoteDataSource: RemoteDataSource, private val database: AppDatabase): BaseRepository(), ArticleRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticlePagingData(articleType: ArticleType, categoryId:Int, query: String): Flow<PagingData<Article>> {
        return Pager(config = AppCfg.pagingCfg,
                remoteMediator= ArticleRemoteMediator(articleType = articleType,
                categoryId = categoryId,
                query=query,
                database=database,
                remoteDataSource=remoteDataSource),
            pagingSourceFactory = {database.articleDao.pagingSource(articleType.type,categoryId)}).flow.map { it -> it.map { it.toArticle() } }
    }

    override suspend fun getBanner() = exec {
        remoteDataSource.getBanner()
    }

    override suspend fun collectArticle(articleId: Int): Any? {
        val resp = exec { remoteDataSource.collect(articleId) }
        database.articleDao.updateCollectState(articleId, 1)
        return resp
    }

    override suspend fun unCollectArticle(articleId: Int): Any? {
        val resp = exec { remoteDataSource.unCollect(articleId) }
        database.articleDao.updateCollectState(articleId, 0)
        return resp
    }

    override suspend fun getProjectArticleCategories(): List<ArticleCategory> {
        return exec{remoteDataSource.getProjectArticleCategories()}
    }

    override suspend fun getPublicNumberArticleCategories(): List<ArticleCategory> {
        return exec{ remoteDataSource.getPublicNumberArticleCategories() }
    }

    override suspend fun addArticle(title:String, link:String): Any? {
        return exec{remoteDataSource.addArticle(title, link)}
    }

    override suspend fun deleteArticle(article: Article) {
        database.articleDao.delete(article.toEntity())
    }
}