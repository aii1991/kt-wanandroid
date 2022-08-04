package com.zjh.ktwanandroid.data.repository.imp

import androidx.paging.PagingData
import com.zjh.ktwanandroid.data.datasource.local.AppDatabase
import com.zjh.ktwanandroid.data.datasource.remote.RemoteDataSource
import com.zjh.ktwanandroid.data.repository.ArticleRepository
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.ArticleCategory
import com.zjh.ktwanandroid.domain.model.enums.ArticleType
import com.zjh.ktwanandroid.domain.repository.PublicNumberRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author zjh
 * 2022/7/15
 */
class PublicNumberRepositoryImp(remoteDataSource: RemoteDataSource, database: AppDatabase) : BaseRepository(), PublicNumberRepository {
    private val articleRepository: ArticleRepository = ArticleRepositoryImp(remoteDataSource,database)

    override suspend fun getArticleCategories(): List<ArticleCategory> {
        return articleRepository.getPublicNumberArticleCategories()
    }

    override fun getArticleByCategoryId(cId: Int): Flow<PagingData<Article>> {
        return articleRepository.getArticlePagingData(articleType = ArticleType.PUBLIC_NUMBER, categoryId = cId)
    }

    override suspend fun collectArticle(articleId: Int): Any? {
        return articleRepository.collectArticle(articleId)
    }

    override suspend fun unCollectArticle(articleId: Int): Any? {
        return articleRepository.unCollectArticle(articleId)
    }
}