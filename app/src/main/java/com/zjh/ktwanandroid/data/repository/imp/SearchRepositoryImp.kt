package com.zjh.ktwanandroid.data.repository.imp

import androidx.paging.PagingData
import com.zjh.ktwanandroid.data.datasource.local.AppDatabase
import com.zjh.ktwanandroid.data.datasource.remote.RemoteDataSource
import com.zjh.ktwanandroid.data.mapper.toEntity
import com.zjh.ktwanandroid.data.mapper.toSearchData
import com.zjh.ktwanandroid.data.repository.ArticleRepository
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.SearchData
import com.zjh.ktwanandroid.domain.model.enums.ArticleType
import com.zjh.ktwanandroid.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author zjh
 * 2022/7/25
 */
class SearchRepositoryImp(val remoteDataSource: RemoteDataSource,val database: AppDatabase) : BaseRepository(), SearchRepository {
    private val articleRepository: ArticleRepository = ArticleRepositoryImp(remoteDataSource,database)

    override suspend fun getHotTagListData(): List<SearchData> {
        return exec { remoteDataSource.getSearchHotTagListData() }
    }

    override suspend fun getHistoryListData(): List<SearchData> {
        return database.searchDao.findAll().map { it.toSearchData() }
    }

    override suspend fun clearHistory() {
        database.searchDao.clear()
    }

    override suspend fun deleteHistoryData(name: String) {
        database.searchDao.deleteByName(name)
    }

    override suspend fun addToHistory(searchData: SearchData) {
        database.searchDao.insert(searchData.toEntity())
    }

    override suspend fun collectArticle(articleId: Int): Any? {
        return articleRepository.collectArticle(articleId)
    }

    override suspend fun unCollectArticle(articleId: Int): Any? {
        return articleRepository.unCollectArticle(articleId)
    }

    override fun getArticlePagingData(query: String): Flow<PagingData<Article>> {
        return articleRepository.getArticlePagingData(ArticleType.SEARCH,0,query)
    }
}