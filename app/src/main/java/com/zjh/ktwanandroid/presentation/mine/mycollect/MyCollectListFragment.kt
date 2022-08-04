package com.zjh.ktwanandroid.presentation.mine.mycollect

import com.zjh.ktwanandroid.databinding.FragmentMyCollectListBinding
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.presentation.AbsArticleFragment
import com.zjh.ktwanandroid.presentation.ArticleListIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyCollectListFragment : AbsArticleFragment<MyCollectVM,FragmentMyCollectListBinding>() {

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            mViewModel.mMyCollectArticleList.collectLatest {
                mArticleAdapter.submitData(it)
            }
        }
    }

    override fun collectArticle(article: Article, position: Int) {
        mViewModel.dispatchIntent(ArticleListIntent.CollectArticle(article,position))
    }

    override fun unCollectArticle(article: Article, position: Int) {
        mViewModel.dispatchIntent(ArticleListIntent.UnCollectArticle(article,position))
    }

}