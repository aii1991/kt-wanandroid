package com.zjh.ktwanandroid.presentation.tree

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zjh.ktwanandroid.KEY_ARTICLE_TYPE
import com.zjh.ktwanandroid.databinding.FragmentTreeSubListBinding
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.enums.ArticleType
import com.zjh.ktwanandroid.presentation.AbsArticleFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TreeSubListFragment : AbsArticleFragment<TreeVM,FragmentTreeSubListBinding>() {
    private var aType: Int = ArticleType.TREE_SQUARE.type

    companion object{
        fun newInstance(articleType: ArticleType): Fragment {
            return TreeSubListFragment().apply {
                arguments = Bundle().apply { putInt(KEY_ARTICLE_TYPE, articleType.type) } }
        }
    }

    override fun getArguments(args: Bundle?) {
        args?.run {
            aType = getInt(KEY_ARTICLE_TYPE)
        }
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            when(aType){
                ArticleType.TREE_ASK.type ->{
                    mViewModel.mAskArticlePagingData.collectLatest { mArticleAdapter.submitData(it) }
                }
                else ->{
                    mViewModel.mSquareArticlePagingData.collectLatest { mArticleAdapter.submitData(it) }
                }
            }
        }
    }

    override fun collectArticle(article: Article, position: Int) {
        mViewModel.collectArticle(article)
    }

    override fun unCollectArticle(article: Article, position: Int) {
        mViewModel.unCollectArticle(article)
    }

}