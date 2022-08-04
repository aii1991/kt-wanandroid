package com.zjh.ktwanandroid.presentation.publicnumber

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.repository.PublicNumberRepository
import com.zjh.ktwanandroid.presentation.ArticleListVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * @author zjh
 * 2022/7/15
 */
@HiltViewModel
class PublicNumberVM @Inject constructor(private val publicNumberRepository: PublicNumberRepository)
    : ArticleListVM<PublicNumberUiState>(){
    val mUiState = getUiState().asStateFlow()
    var cId = 0

    val mArticlePagingData = lazy {
        publicNumberRepository.getArticleByCategoryId(cId).cachedIn(viewModelScope)
    }

    private fun loadCategories() = launch(true){
        val articleCategories = publicNumberRepository.getArticleCategories()
        setData { it.copy(articleCategories = articleCategories) }
    }

    override fun unCollectArticle(article: Article) = launch(true){
        publicNumberRepository.unCollectArticle(articleId = article.id)
    }

    override fun collectArticle(article: Article) = launch(true){
        publicNumberRepository.collectArticle(articleId = article.id)
    }

    override fun createUiState(): PublicNumberUiState {
        return PublicNumberUiState()
    }

    override fun loadData() {
        loadCategories()
    }
}