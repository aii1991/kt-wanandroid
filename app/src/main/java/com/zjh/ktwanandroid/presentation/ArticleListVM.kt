package com.zjh.ktwanandroid.presentation

import com.zjh.ktwanandroid.app.base.BaseMVIVM
import com.zjh.ktwanandroid.domain.model.Article
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * @author zjh
 * 2022/8/2
 */
abstract class ArticleListVM<UiState> : BaseMVIVM<UiState, ArticleListIntent>() {

    override suspend fun handleIntent(sharedFlow: MutableSharedFlow<ArticleListIntent>) {
        sharedFlow.collectLatest {
            when(it){
                is ArticleListIntent.LoadData-> loadData()
                is ArticleListIntent.RefreshData-> loadData()
                is ArticleListIntent.CollectArticle->{
                    collectArticle(it.article)
                }
                is ArticleListIntent.UnCollectArticle->{
                    unCollectArticle(it.article)
                }
            }
        }
    }

    abstract fun unCollectArticle(article: Article)

    abstract fun collectArticle(article: Article)

    open fun loadData(){}
}