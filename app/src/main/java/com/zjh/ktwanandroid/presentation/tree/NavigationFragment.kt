package com.zjh.ktwanandroid.presentation.tree

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseMVIFragment
import com.zjh.ktwanandroid.app.widget.itemDecoration.SpaceItemDecoration
import com.zjh.ktwanandroid.databinding.FragmentNavigationBinding
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.presentation.adapter.NavigationAdapter
import com.zjh.ktwanandroid.presentation.webview.WebFragment
import dagger.hilt.android.AndroidEntryPoint
import init
import initFloatBtn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

@AndroidEntryPoint
class NavigationFragment : BaseMVIFragment<TreeVM,FragmentNavigationBinding>() {
    private val mNavigationAdapter: NavigationAdapter by lazy { NavigationAdapter(arrayListOf()) }

    override fun setupView(savedInstanceState: Bundle?) {
        mDatabind.run {
            includeRecyclerview.recyclerView.init(LinearLayoutManager(context),mNavigationAdapter).let {
                it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
                it.initFloatBtn(floatbtn)
            }

            includeRecyclerview.swipeRefresh.init{
                mViewModel.dispatchIntent(TreeIntent.RefreshArticleNavigationData)
            }

            mNavigationAdapter.run {
                onItemClick = { item: Article, _: View, _: Int ->
                    nav().navigateAction(R.id.action_main_fragment_to_webFragment,Bundle().apply {
                        putParcelable(WebFragment.ParamKey.ARTICLE_KEY,item)
                    })
                }
            }
        }
    }

    override fun lazyLoadData() {
        mViewModel.dispatchIntent(TreeIntent.LoadArticleNavigationData)
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        mViewModel.mUiState.collectLatest {
            if(it.articleNavigationListData.isNotEmpty()){
                mNavigationAdapter.data.addAll(it.articleNavigationListData)
                mNavigationAdapter.notifyItemRangeInserted(0,it.articleNavigationListData.size)
            }else{
                mNavigationAdapter.setList(arrayListOf())
            }
            mDatabind.includeRecyclerview.swipeRefresh.isRefreshing = false
        }
    }
}