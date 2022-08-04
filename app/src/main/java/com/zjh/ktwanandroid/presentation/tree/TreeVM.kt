package com.zjh.ktwanandroid.presentation.tree

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zjh.ktwanandroid.app.base.BaseMVIVM
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.repository.TreeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * @author zjh
 * 2022/7/20
 */
@HiltViewModel
class TreeVM @Inject constructor(private val treeRepository: TreeRepository)
    : BaseMVIVM<TreeUiState, TreeIntent>(){
    val mUiState = getUiState().asStateFlow()

    var cId = 0
    val mSquareArticlePagingData = treeRepository.getSquareArticleList().cachedIn(viewModelScope)
    val mAskArticlePagingData = treeRepository.getAskArticleList().cachedIn(viewModelScope)
    val mHierarchyArticleList by lazy {
        treeRepository.getHierarchyArticleList(cId)
    }


    private fun getArticleHierarchyData(isShowLoading: Boolean = false) = launch(isShowLoading = isShowLoading, onStart = { setData { TreeUiState() } }) {
        val data = treeRepository.getHierarchyData()
        setData { it.copy(articleHierarchyListData = data) }
    }

    private fun getArticleNavigationData(isShowLoading: Boolean = false) = launch(isShowLoading = isShowLoading, onStart = { setData { TreeUiState() } }) {
        val data = treeRepository.getArticleNavigationData()
        setData { it.copy(articleNavigationListData = data) }
    }

    fun unCollectArticle(article: Article) = launch(true){
        treeRepository.unCollectArticle(articleId = article.id)
    }

    fun collectArticle(article: Article) = launch(true){
        treeRepository.collectArticle(articleId = article.id)
    }

    override fun createUiState(): TreeUiState {
        return TreeUiState()
    }

    override suspend fun handleIntent(sharedFlow: MutableSharedFlow<TreeIntent>) {
        sharedFlow.collectLatest {
            when(it){
                is TreeIntent.LoadArticleHierarchyData-> getArticleHierarchyData(true)
                is TreeIntent.RefreshArticleHierarchyData-> getArticleHierarchyData()
                is TreeIntent.LoadArticleNavigationData-> getArticleNavigationData(true)
                is TreeIntent.RefreshArticleNavigationData-> getArticleNavigationData()
                is TreeIntent.CollectArticle->{
                    collectArticle(it.article)
                }
                is TreeIntent.UnCollectArticle->{
                    unCollectArticle(it.article)
                }
            }
        }
    }

}