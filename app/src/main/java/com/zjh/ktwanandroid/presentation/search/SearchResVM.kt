package com.zjh.ktwanandroid.presentation.search

import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.repository.SearchRepository
import com.zjh.ktwanandroid.presentation.ArticleListVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author zjh
 * 2022/7/26
 */
@HiltViewModel
class SearchResVM @Inject constructor(private val searchRepository: SearchRepository): ArticleListVM<SearchResUiState>() {
    var searchText: String = ""

    val mLastArticlePagingData by lazy {
        searchRepository.getArticlePagingData(searchText)
    }

    override fun unCollectArticle(article: Article) = launch(true){
        searchRepository.unCollectArticle(articleId = article.id)
    }

    override fun collectArticle(article: Article) = launch(true){
        searchRepository.collectArticle(articleId = article.id)
    }

    override fun createUiState(): SearchResUiState {
        return SearchResUiState()
    }
}