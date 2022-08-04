package com.zjh.ktwanandroid.presentation.tree

import android.os.Bundle
import com.zjh.ktwanandroid.KEY_CID
import com.zjh.ktwanandroid.databinding.FragmentHierarchyTabContentBinding
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.presentation.AbsArticleFragment
import com.zjh.ktwanandroid.presentation.ArticleListIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HierarchyTabContentFragment : AbsArticleFragment<TreeVM,FragmentHierarchyTabContentBinding>() {
    companion object {
        fun newInstance(cId: Int) =
            HierarchyTabContentFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_CID,cId)
                }
            }
    }

    override fun getArguments(args: Bundle?) {
        args?.run {
            mViewModel.cId = getInt(KEY_CID)
        }
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            mViewModel.mHierarchyArticleList.collectLatest {
                mArticleAdapter.submitData(it)
            }
        }
    }

    override fun collectArticle(article: Article, position: Int) {
        mViewModel.dispatchIntent(TreeIntent.CollectArticle(article,position))
    }

    override fun unCollectArticle(article: Article, position: Int) {
        mViewModel.dispatchIntent(TreeIntent.UnCollectArticle(article,position))
    }
}