package com.zjh.ktwanandroid.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.zhpan.bannerview.BannerViewPager
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.databinding.FragmentHomeBinding
import com.zjh.ktwanandroid.databinding.IncludeBannerBinding
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.Banner
import com.zjh.ktwanandroid.presentation.AbsArticleFragment
import com.zjh.ktwanandroid.presentation.ArticleListIntent
import com.zjh.ktwanandroid.presentation.adapter.BannerAdapter
import com.zjh.ktwanandroid.presentation.webview.WebFragment
import dagger.hilt.android.AndroidEntryPoint
import init
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

/**
 * 首页fragment
 */
@AndroidEntryPoint
class HomeFragment : AbsArticleFragment<HomeVM, FragmentHomeBinding>() {
    private val includeBannerBinding by lazy {
        IncludeBannerBinding.inflate(LayoutInflater.from(context))
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)
        mDatabind.includeToolbar.toolbar.run {
            init("首页")
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_search -> {
                        nav().navigateAction(R.id.action_main_fragment_to_searchFragment)
                    }
                }
                true
            }
        }

        @Suppress("UNCHECKED_CAST")
        (includeBannerBinding.bannerView as BannerViewPager<Banner, BannerAdapter.BannerViewHolder>).init(
            BannerAdapter(), this){
            nav().navigateAction(R.id.webFragment,Bundle().apply { putParcelable(WebFragment.ParamKey.BANNER_KEY,it) })
        }
    }

    override fun lazyLoadData() {
        mViewModel.dispatchIntent(ArticleListIntent.LoadData)
    }

    @SuppressLint("NotifyDataSetChanged")
    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            mViewModel.mPagingData.collectLatest {
                mArticleAdapter.submitData(it)
            }
        }

        coroutineScope.launch{
            mViewModel.mUiState.collect{
                if(it.listBanner != null){
                    includeBannerBinding.bannerView.refreshData(it.listBanner)
                }
            }
        }
    }

    override fun isAddItemMarginTop(): Boolean {
        return false
    }

    override fun onRefresh() {
        mViewModel.dispatchIntent(ArticleListIntent.RefreshData)
    }

    override fun getHeaderViews(): List<View> {
        return listOf(includeBannerBinding.root)
    }

    override fun collectArticle(article: Article, position: Int) {
        mViewModel.dispatchIntent(ArticleListIntent.CollectArticle(article,position))
    }

    override fun unCollectArticle(article: Article, position: Int) {
        mViewModel.dispatchIntent(ArticleListIntent.UnCollectArticle(article,position))
    }
}

