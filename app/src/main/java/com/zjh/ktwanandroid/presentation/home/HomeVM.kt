package com.zjh.ktwanandroid.presentation.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.repository.HomeRepository
import com.zjh.ktwanandroid.presentation.ArticleListVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * @author zjh
 * 2022/5/20
 */
@HiltViewModel
class HomeVM @Inject constructor(private val homeRepository: HomeRepository) :
    ArticleListVM<HomeUiState>() {
    val mUiState = getUiState().asStateFlow()
    val mPagingData = homeRepository.getArticlePagingData().cachedIn(viewModelScope)


    private fun loadBanner() = launch{
        val listBanner = homeRepository.getBanner()
        setData { it.copy(listBanner = listBanner) }
    }

    override fun unCollectArticle(article: Article) = launch(true){
        homeRepository.unCollectArticle(articleId = article.id)
    }

    override fun collectArticle(article: Article) = launch(true){
        homeRepository.collectArticle(articleId = article.id)
    }

    override fun createUiState(): HomeUiState {
        return HomeUiState()
    }

    override fun loadData() {
        loadBanner()
    }
}

