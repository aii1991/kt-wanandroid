package com.zjh.ktwanandroid.presentation.webview

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import com.just.agentweb.AgentWeb
import com.zjh.ktwanandroid.app.base.BaseFragment
import com.zjh.ktwanandroid.databinding.FragmentWebBinding
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.Banner
import com.zjh.ktwanandroid.domain.model.CollectArticle
import com.zjh.ktwanandroid.domain.model.CollectArticleUrl
import com.zjh.ktwanandroid.domain.model.enums.CollectType
import com.zjh.ktwanandroid.presentation.webview.WebFragment.ParamKey.ARTICLE_KEY
import com.zjh.ktwanandroid.presentation.webview.WebFragment.ParamKey.BANNER_KEY
import com.zjh.ktwanandroid.presentation.webview.WebFragment.ParamKey.COLLECT_KEY
import com.zjh.ktwanandroid.presentation.webview.WebFragment.ParamKey.COLLECT_URL_KEY
import hideSoftKeyboard
import initClose
import me.hgj.jetpackmvvm.ext.nav

class WebFragment : BaseFragment<WebViewModel, FragmentWebBinding>() {
    private var mAgentWeb: AgentWeb? = null
    private var preWeb: AgentWeb.PreAgentWeb? = null

    object ParamKey{
        const val ARTICLE_KEY = "article_key"
        const val BANNER_KEY = "banner_key"
        const val COLLECT_KEY = "collect_key"
        const val COLLECT_URL_KEY = "collect_url_key"
    }

    override fun initView(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        arguments?.run {
            //点击文章进来的
            getParcelable<Article>(ARTICLE_KEY)?.let {
                mViewModel.ariticleId = it.id
                mViewModel.showTitle = it.title.toString()
                mViewModel.collect = it.collect
                mViewModel.url = it.link.toString()
                mViewModel.collectType = CollectType.Article.type
            }
            //点击首页轮播图进来的
            getParcelable<Banner>(BANNER_KEY)?.let {
                mViewModel.ariticleId = it.id
                mViewModel.showTitle = it.title
                //从首页轮播图 没法判断是否已经收藏过，所以直接默认没有收藏
                mViewModel.collect = false
                mViewModel.url = it.url
                mViewModel.collectType = CollectType.Url.type
            }
            //从收藏文章列表点进来的
            getParcelable<CollectArticle>(COLLECT_KEY)?.let {
                mViewModel.ariticleId = it.originId
                mViewModel.showTitle = it.title
                //从收藏列表过来的，肯定 是 true 了
                mViewModel.collect = true
                mViewModel.url = it.link
                mViewModel.collectType = CollectType.Article.type
            }
            //点击收藏网址列表进来的
            getParcelable<CollectArticleUrl>(COLLECT_URL_KEY)?.let {
                mViewModel.ariticleId = it.id
                mViewModel.showTitle = it.name
                //从收藏列表过来的，肯定 是 true 了
                mViewModel.collect = true
                mViewModel.url = it.link
                mViewModel.collectType = CollectType.Url.type
            }
        }

        mDatabind.includeToolbar.toolbar.run {
            //设置menu 关键代码
            mActivity.setSupportActionBar(this)
            initClose(mViewModel.showTitle) {
                hideSoftKeyboard(activity)
                mAgentWeb?.let { web ->
                    if (web.webCreator.webView.canGoBack()) {
                        web.webCreator.webView.goBack()
                    } else {
                        nav().navigateUp()
                    }
                }
            }
        }
        preWeb = AgentWeb.with(this)
            .setAgentWebParent(mDatabind.webcontent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
    }

    override fun lazyLoadData() {
        //加载网页
        mAgentWeb = preWeb?.go(mViewModel.url)
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mAgentWeb?.let { web ->
                        if (web.webCreator.webView.canGoBack()) {
                            web.webCreator.webView.goBack()
                        } else {
                            nav().navigateUp()
                        }
                    }
                }
            })
    }
}