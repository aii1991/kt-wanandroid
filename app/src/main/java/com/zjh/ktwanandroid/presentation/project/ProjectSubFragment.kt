package com.zjh.ktwanandroid.presentation.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zjh.ktwanandroid.KEY_CID
import com.zjh.ktwanandroid.databinding.FragmentProjectSubBinding
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.presentation.AbsArticleFragment
import com.zjh.ktwanandroid.presentation.ArticleListIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProjectSubFragment : AbsArticleFragment<ProjectVM, FragmentProjectSubBinding>() {
    companion object{
        fun newInstance(cId: Int): Fragment{
            return ProjectSubFragment().apply {
                arguments = Bundle().apply { putInt(KEY_CID, cId) } }
        }
    }

    private var cId:Int = 0

    override fun getArguments(args: Bundle?) {
        args?.run {
            cId = getInt(KEY_CID)
            mViewModel.cId = cId
        }
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            if(cId == 0){
                mViewModel.mLastArticlePagingData.collectLatest { mArticleAdapter.submitData(it) }
            }else{
                mViewModel.mArticlePagingData.collectLatest { mArticleAdapter.submitData(it) }
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