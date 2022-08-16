package com.zjh.ktwanandroid.presentation.tree


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.zjh.ktwanandroid.KEY_ARTICLE_HIERARCHY
import com.zjh.ktwanandroid.KEY_TAB_INDEX
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseMVIFragment
import com.zjh.ktwanandroid.app.widget.itemdecoration.SpaceItemDecoration
import com.zjh.ktwanandroid.databinding.FragmentHierarchyBinding
import com.zjh.ktwanandroid.domain.model.ArticleHierarchy
import com.zjh.ktwanandroid.presentation.adapter.HierarchyAdapter
import dagger.hilt.android.AndroidEntryPoint
import init
import initFloatBtn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

@AndroidEntryPoint
class HierarchyFragment : BaseMVIFragment<TreeVM,FragmentHierarchyBinding>() {

    private val mHierarchyAdapter: HierarchyAdapter by lazy { HierarchyAdapter(arrayListOf()) }

    override fun setupView(savedInstanceState: Bundle?) {
        mDatabind.run {
            includeRecyclerview.recyclerView.init(LinearLayoutManager(context),mHierarchyAdapter).let {
                it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
                it.initFloatBtn(floatbtn)
            }

            includeRecyclerview.swipeRefresh.init{
                mViewModel.dispatchIntent(TreeIntent.RefreshArticleHierarchyData)
            }

            mHierarchyAdapter.run {
                setOnItemClickListener { _, _, position ->
                    if(mHierarchyAdapter.data[position].children.isNotEmpty()){
                        nav().navigateAction(R.id.hierarchyTabFragment,Bundle().apply {
                            putParcelable(KEY_ARTICLE_HIERARCHY,mHierarchyAdapter.data[position])
                        })
                    }
                }
                onItemClick = { item: ArticleHierarchy, _: View, position: Int ->
                    nav().navigateAction(R.id.hierarchyTabFragment,Bundle().apply {
                        putParcelable(KEY_ARTICLE_HIERARCHY,item)
                        putInt(KEY_TAB_INDEX, position)
                    })
                }
            }
        }
    }

    override fun lazyLoadData() {
        mViewModel.dispatchIntent(TreeIntent.LoadArticleHierarchyData)
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        mViewModel.mUiState.collectLatest {
            mHierarchyAdapter.setList(it.articleHierarchyListData)
            mDatabind.includeRecyclerview.swipeRefresh.isRefreshing = false
        }
    }

}