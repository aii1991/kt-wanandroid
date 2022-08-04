package com.zjh.ktwanandroid.presentation

import android.os.Bundle
import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ConvertUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseMVIFragment
import com.zjh.ktwanandroid.app.base.BaseMVIVM
import com.zjh.ktwanandroid.app.base.FooterAdapter
import com.zjh.ktwanandroid.app.ext.jumpByLogin
import com.zjh.ktwanandroid.app.widget.itemDecoration.SpaceItemDecoration
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.presentation.adapter.ArticleAdapter
import com.zjh.ktwanandroid.presentation.webview.WebFragment
import init
import initColorScheme
import initFloatBtn
import kotlinx.coroutines.flow.collectLatest
import loadServiceInit
import me.hgj.jetpackmvvm.ext.launchRepeatOnLifecycle
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import showEmpty
import showError
import showLoading

/**
 * @author zjh
 * 2022/7/22
 */
abstract class AbsArticleFragment<VM: BaseMVIVM<*, *>,DB: ViewBinding> : BaseMVIFragment<VM,DB>() {
    lateinit var mArticleAdapter: ArticleAdapter
    private lateinit var mSwipeRefresh: SwipeRefreshLayout

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    open fun getHeaderViews(): List<View>?{
        return null
    }
    open fun onRefresh(){}
    open fun isAddItemMarginTop():Boolean{
        return true
    }

    abstract fun collectArticle(article: Article, position:Int)
    abstract fun unCollectArticle(article: Article, position:Int)


    override fun setupView(savedInstanceState: Bundle?) {

        mArticleAdapter = ArticleAdapter({
            nav().navigateAction(R.id.webFragment,Bundle().apply { putParcelable(WebFragment.ParamKey.ARTICLE_KEY,it) })
        },{item,_,layoutPosition->
            val p = layoutPosition - mArticleAdapter.headerViewCount
            item?.let {
                    article->
                nav().jumpByLogin(actionLogin ={
                    nav().navigateAction(R.id.loginFragment)
                }){
                    if(article.collect) unCollectArticle(article, p)
                    else collectArticle(article, p)
                }
            }
        })

        mDatabind.root.apply {
            val swipeRecyclerView: SwipeRecyclerView = findViewById(R.id.recyclerView)
            val floatBtn: FloatingActionButton = findViewById(R.id.floatbtn)
            val swipeRefresh: SwipeRefreshLayout = swipeRecyclerView.parent as SwipeRefreshLayout

            swipeRecyclerView.init(LinearLayoutManager(context),mArticleAdapter,
                FooterAdapter{ mArticleAdapter.refresh() }).apply{
                addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), isAddItemMarginTop()))
                initFloatBtn(floatBtn)
                getHeaderViews()?.let {
                        views->
                    views.forEach { v-> addHeaderView(v) }
                    mArticleAdapter.headerViewCount = headerCount
                }
            }

            mSwipeRefresh = swipeRefresh.apply {
                initColorScheme()
                setOnRefreshListener {
                    onRefresh()
                    mArticleAdapter.refresh()
                }
            }

            loadsir = loadServiceInit(mSwipeRefresh) {
                //点击重试时触发的操作
                loadsir.showLoading()
                onRefresh()
            }

            launchRepeatOnLifecycle{
                mArticleAdapter.loadStateFlow.collectLatest { loadStates ->
                    val isLoading = loadStates.refresh is LoadState.Loading
                    val isErr = loadStates.refresh is LoadState.Error

                    if(isErr){
                        loadsir.showError()
                    }else{
                        mSwipeRefresh.isRefreshing = isLoading
                        if (isLoading){
                            if(mArticleAdapter.itemCount == 0){
                                loadsir.showLoading()
                            }
                        }else{
                            if(loadStates.refresh.endOfPaginationReached && mArticleAdapter.itemCount < 1){
                                loadsir.showEmpty()
                            }else{
                                loadsir.showSuccess()
                            }
                        }
                    }
                }
            }
        }
    }
}