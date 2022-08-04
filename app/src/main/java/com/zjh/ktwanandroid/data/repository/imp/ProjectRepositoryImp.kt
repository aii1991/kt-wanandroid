package com.zjh.ktwanandroid.data.repository.imp

import androidx.paging.PagingData
import com.zjh.ktwanandroid.data.datasource.local.AppDatabase
import com.zjh.ktwanandroid.data.datasource.remote.RemoteDataSource
import com.zjh.ktwanandroid.data.repository.ArticleRepository
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.ArticleCategory
import com.zjh.ktwanandroid.domain.model.enums.ArticleType
import com.zjh.ktwanandroid.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author zjh
 * 2022/7/11
 */
class ProjectRepositoryImp(remoteDataSource: RemoteDataSource, database: AppDatabase): BaseRepository(), ProjectRepository {
    private val articleRepository: ArticleRepository = ArticleRepositoryImp(remoteDataSource,database)

    override suspend fun getArticleCategories(): List<ArticleCategory> {
        return articleRepository.getProjectArticleCategories()
    }

    override fun getArticleByCategoryId(cId: Int): Flow<PagingData<Article>> {
        return articleRepository.getArticlePagingData(articleType = ArticleType.PROJECT, categoryId = cId)
    }

    override fun getLastArticleList(): Flow<PagingData<Article>> {
        return articleRepository.getArticlePagingData(articleType = ArticleType.PROJECT)
    }

    override suspend fun collectArticle(articleId: Int): Any? {
        return articleRepository.collectArticle(articleId)
    }

    override suspend fun unCollectArticle(articleId: Int): Any? {
        return articleRepository.unCollectArticle(articleId)
    }
}