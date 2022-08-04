package com.zjh.ktwanandroid.presentation.mine.mycollect

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.repository.MineRepository
import com.zjh.ktwanandroid.presentation.ArticleListVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * @author zjh
 * 2022/7/29
 */
@HiltViewModel
class MyCollectVM @Inject constructor(private val mineRepository: MineRepository) : ArticleListVM<MyCollectUiState>(){
    val mUiState = getUiState().asStateFlow()
    val mMyCollectArticleList = mineRepository.getMyCollectArticleList().cachedIn(viewModelScope)
    var isCollectUrl = false

    override fun createUiState(): MyCollectUiState {
        return MyCollectUiState()
    }

    private fun loadMyCollectUrlList() = launch {
        setData { it.copy(isLoading = true) }
        val collectUrlList = mineRepository.getMyCollectUrlList()
        setData { it.copy(isLoading = false, collectUrlList = collectUrlList) }
    }

    override fun unCollectArticle(article: Article) = launch(true){
        if(isCollectUrl){
            mineRepository.unCollectArticle(articleId = article.id)
        }else{
            mineRepository.unCollectArticle(articleId = article.id)
            mineRepository.deleteArticle(article)
        }
    }

    override fun collectArticle(article: Article)  = launch(true) {
        mineRepository.collectArticle(articleId = article.id)
    }

    override fun loadData() {
        loadMyCollectUrlList()
    }
}