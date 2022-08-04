package com.zjh.ktwanandroid.presentation.project

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.repository.ProjectRepository
import com.zjh.ktwanandroid.presentation.ArticleListVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * @author zjh
 * 2022/7/7
 */
@HiltViewModel
class ProjectVM @Inject constructor(private val projectRepository: ProjectRepository) :
    ArticleListVM<ProjectUiState>() {
    val mUiState = getUiState().asStateFlow()
    var cId = 0

    val mLastArticlePagingData = projectRepository.getLastArticleList().cachedIn(viewModelScope)
    val mArticlePagingData by lazy {
        projectRepository.getArticleByCategoryId(cId).cachedIn(viewModelScope)
    }

    private fun loadCategories() = launch(true){
        val articleCategories = projectRepository.getArticleCategories()
        setData { it.copy(articleCategories = articleCategories) }
    }

    override fun unCollectArticle(article: Article) = launch(true){
        projectRepository.unCollectArticle(articleId = article.id)
    }

    override fun collectArticle(article: Article) = launch(true){
        projectRepository.collectArticle(articleId = article.id)
    }

    override fun loadData() {
        loadCategories()
    }


    override fun createUiState(): ProjectUiState {
        return ProjectUiState()
    }
}