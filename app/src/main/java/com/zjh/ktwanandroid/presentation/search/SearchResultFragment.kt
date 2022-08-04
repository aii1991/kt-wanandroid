package com.zjh.ktwanandroid.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zjh.ktwanandroid.SEARCH_KEY
import com.zjh.ktwanandroid.databinding.FragmentSearchResultBinding
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.presentation.AbsArticleFragment
import com.zjh.ktwanandroid.presentation.ArticleListIntent
import dagger.hilt.android.AndroidEntryPoint
import initClose
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.ext.nav


@AndroidEntryPoint
class SearchResultFragment : AbsArticleFragment<SearchResVM,FragmentSearchResultBinding>() {
    companion object{
        fun newInstance(searchText: String): Fragment {
            return SearchResultFragment().apply {
                arguments = Bundle().apply { putString(SEARCH_KEY, searchText) } }
        }
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)
        mDatabind.includeToolbar.toolbar.initClose(mViewModel.searchText){
            nav().navigateUp()
        }
    }

    override fun getArguments(args: Bundle?) {
        args?.run {
            mViewModel.searchText = getString(SEARCH_KEY,"")
        }
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            mViewModel.mLastArticlePagingData.collectLatest {
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