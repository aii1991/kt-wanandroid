package com.zjh.ktwanandroid.presentation.webview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.Window
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.VibrateUtils
import com.just.agentweb.AgentWeb
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseMVIFragment
import com.zjh.ktwanandroid.data.datasource.local.DataStore
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
import dagger.hilt.android.AndroidEntryPoint
import hideSoftKeyboard
import initClose
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import me.hgj.jetpackmvvm.ext.nav

@AndroidEntryPoint
class WebFragment : BaseMVIFragment<WebViewModel, FragmentWebBinding>() {
    private var mAgentWeb: AgentWeb? = null
    private var preWeb: AgentWeb.PreAgentWeb? = null

    object ParamKey{
        const val ARTICLE_KEY = "article_key"
        const val BANNER_KEY = "banner_key"
        const val COLLECT_KEY = "collect_key"
        const val COLLECT_URL_KEY = "collect_url_key"
    }

    override fun setupView(savedInstanceState: Bundle?) {
        initWebView()
    }

    private fun initWebView() {
        setHasOptionsMenu(true)
        arguments?.run {
            //点击文章进来的
            getParcelable<Article>(ARTICLE_KEY)?.let {
                mViewModel.articleId = it.id
                mViewModel.showTitle = it.title.toString()
                mViewModel.isCollect = it.collect
                mViewModel.url = it.link.toString()
                mViewModel.collectType = CollectType.Article.type
            }
            //点击首页轮播图进来的
            getParcelable<Banner>(BANNER_KEY)?.let {
                mViewModel.articleId = it.id
                mViewModel.showTitle = it.title
                //从首页轮播图 没法判断是否已经收藏过，所以直接默认没有收藏
                mViewModel.isCollect = false
                mViewModel.url = it.url
                mViewModel.collectType = CollectType.Url.type
            }
            //从收藏文章列表点进来的
            getParcelable<CollectArticle>(COLLECT_KEY)?.let {
                mViewModel.articleId = it.originId
                mViewModel.showTitle = it.title
                //从收藏列表过来的，肯定 是 true 了
                mViewModel.isCollect = true
                mViewModel.url = it.link
                mViewModel.collectType = CollectType.Article.type
            }
            //点击收藏网址列表进来的
            getParcelable<CollectArticleUrl>(COLLECT_URL_KEY)?.let {
                mViewModel.articleId = it.id
                mViewModel.showTitle = it.name
                //从收藏列表过来的，肯定 是 true 了
                mViewModel.isCollect = true
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

    override fun lazyLoadData() {
        //加载网页
        mAgentWeb = preWeb?.go(mViewModel.url)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.web_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        //如果收藏了，右上角的图标相对应改变
        context?.let {
            if (mViewModel.isCollect) {
                menu.findItem(R.id.web_collect).icon =
                    ContextCompat.getDrawable(it, R.drawable.ic_collected)
            } else {
                menu.findItem(R.id.web_collect).icon =
                    ContextCompat.getDrawable(it, R.drawable.ic_collect)
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.web_share -> {
                //分享
                startActivity(Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "{${mViewModel.showTitle}}:${mViewModel.url}")
                    type = "text/plain"
                }, "分享到"))
            }
            R.id.web_refresh -> {
                //刷新网页
                mAgentWeb?.urlLoader?.reload()
            }
            R.id.web_collect -> {
                //点击收藏 震动一下
                VibrateUtils.vibrate(40)
                //是否已经登录了，没登录需要跳转到登录页去
                if (DataStore.isLogin()) {
                    //是否已经收藏了
                    if (mViewModel.isCollect) {
                        mViewModel.dispatchIntent(WebViewIntent.UnCollectArticle(Article(id= mViewModel.articleId)))
                    } else {
                        mViewModel.dispatchIntent(WebViewIntent.CollectArticle(Article(id = mViewModel.articleId)))
                    }
                } else {
                    //跳转到登录页
                    nav().navigate(R.id.action_webFragment_to_loginFragment)
                }
            }
            R.id.web_browser -> {
                //用浏览器打开
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mViewModel.url)))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        mActivity.setSupportActionBar(null)
        super.onDestroy()
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        mViewModel.mUiState.collectLatest {
            mViewModel.isCollect = it.isCollect
            mActivity.window?.invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL)
            mActivity.invalidateOptionsMenu()
        }
    }


}