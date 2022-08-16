package com.zjh.ktwanandroid.presentation.mine.mycollect

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseMVIFragment
import com.zjh.ktwanandroid.app.widget.itemdecoration.SpaceItemDecoration
import com.zjh.ktwanandroid.databinding.FragmentMyCollectListUrlBinding
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.presentation.ArticleListIntent
import com.zjh.ktwanandroid.presentation.adapter.MyCollectUrlAdapter
import com.zjh.ktwanandroid.presentation.webview.WebFragment.ParamKey.COLLECT_KEY
import dagger.hilt.android.AndroidEntryPoint
import init
import initFloatBtn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import loadServiceInit
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import showEmpty
import showLoading

@AndroidEntryPoint
class MyCollectUrlListFragment : BaseMVIFragment<MyCollectVM,FragmentMyCollectListUrlBinding>() {
    private val mAdapter: MyCollectUrlAdapter = MyCollectUrlAdapter(arrayListOf())
    private lateinit var loadsir: LoadService<Any>

    override fun setupView(savedInstanceState: Bundle?) {
        mDatabind.includeRecyclerview.apply {
            swipeRefresh.init { mViewModel.dispatchIntent(ArticleListIntent.LoadData) }
            recyclerView.init(LinearLayoutManager(context),mAdapter).apply {
                addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
                initFloatBtn(mDatabind.floatbtn)
            }
            loadsir = loadServiceInit(swipeRefresh) {
                //点击重试时触发的操作
                loadsir.showLoading()
                mViewModel.dispatchIntent(ArticleListIntent.LoadData)
            }
        }

        mAdapter.run {
            setCollectClick { item, _, position ->
                mViewModel.dispatchIntent(ArticleListIntent.UnCollectArticle(Article(id=item.id),position))
            }
            setOnItemClickListener { _, _, position ->
                nav().navigateAction(R.id.action_main_fragment_to_webFragment, Bundle().apply {
                    putParcelable(COLLECT_KEY, mAdapter.data[position])
                })
            }
        }
    }

    override fun getArguments(args: Bundle?) {
        mViewModel.isCollectUrl = true
    }

    override fun lazyLoadData() {
        mViewModel.dispatchIntent(ArticleListIntent.LoadData)
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        mViewModel.mUiState.collectLatest {
            if(!it.isLoading){
                if(mAdapter.itemCount == 0){
                    loadsir.showEmpty()
                }else{
                    loadsir.showSuccess()
                }
                mAdapter.setList(it.collectUrlList)
                mDatabind.includeRecyclerview.swipeRefresh.isRefreshing = false
            }else{
                if(mAdapter.itemCount > 0){
                    mDatabind.includeRecyclerview.swipeRefresh.isRefreshing = true
                }else{
                    loadsir.showLoading()
                }
            }

        }
    }

}