package com.zjh.ktwanandroid.presentation.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.SEARCH_KEY
import com.zjh.ktwanandroid.app.base.BaseMVIFragment
import com.zjh.ktwanandroid.app.util.SettingUtil
import com.zjh.ktwanandroid.databinding.FragmentSearchBinding
import com.zjh.ktwanandroid.presentation.adapter.SearchHistoryAdapter
import com.zjh.ktwanandroid.presentation.adapter.SearchHotAdapter
import dagger.hilt.android.AndroidEntryPoint
import init
import initClose
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

@AndroidEntryPoint
class SearchFragment : BaseMVIFragment<SearchVM, FragmentSearchBinding>() {
    private val mHistoryAdapter: SearchHistoryAdapter by lazy { SearchHistoryAdapter(arrayListOf()) }
    private val mHotAdapter: SearchHotAdapter by lazy { SearchHotAdapter(arrayListOf()) }
    private lateinit var mToolBar: Toolbar

    override fun setupView(savedInstanceState: Bundle?) {
        mToolBar = mDatabind.includeToolbar.toolbar
        setMenu()

        mDatabind.searchHotRv.init(FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
        }, mHotAdapter, false)
        mDatabind.searchHistoryRv.init(LinearLayoutManager(context), mHistoryAdapter, false)

        mHotAdapter.run {
            setOnItemClickListener { _, _, position ->
                val queryStr = mHotAdapter.data[position].name
                mViewModel.dispatchIntent(SearchIntent.AddToHistory(queryStr))
                toSearchResPage(queryStr)
            }
        }

        mHistoryAdapter.run {
            setOnItemClickListener { _, _, position ->
                toSearchResPage(this.getItem(position).name)
            }
            addChildClickViewIds(R.id.item_history_del)
            setOnItemChildClickListener { _, view, position ->
                when (view.id) {
                    R.id.item_history_del -> {
                        mViewModel.dispatchIntent(SearchIntent.DeleteHistory(mHistoryAdapter.getItem(position).name))
                    }
                }
            }
        }

        mDatabind.searchClear.setOnClickListener {
            activity?.let {
                MaterialDialog(it)
                    .cancelable(false)
                    .lifecycleOwner(this)
                    .show {
                        title(text = "温馨提示")
                        message(text = "确定清空吗?")
                        negativeButton(text = "取消")
                        positiveButton(text = "清空") {
                            //清空
                            mViewModel.dispatchIntent(SearchIntent.ClearHistory)
                        }
                        getActionButton(WhichButton.POSITIVE).updateTextColor(
                            SettingUtil.getColor(it)
                        )
                        getActionButton(WhichButton.NEGATIVE).updateTextColor(
                            SettingUtil.getColor(it)
                        )
                    }
            }
        }

    }

    override fun lazyLoadData() {
        mViewModel.dispatchIntent(SearchIntent.LoadHistoryData)
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        mViewModel.mUiState.collectLatest {
            mDatabind.searchClear.isVisible = it.historyData.isNotEmpty()
            mHistoryAdapter.setList(it.historyData)
            mHotAdapter.setList(it.hotData)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchView = menu.findItem(R.id.action_search)?.actionView as SearchView
        searchView.run {
            maxWidth = Integer.MAX_VALUE
            onActionViewExpanded()
            queryHint = "输入关键字搜索"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                //searchview的监听
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //当点击搜索时 输入法的搜索，和右边的搜索都会触发
                    query?.let { queryStr ->
                        mViewModel.dispatchIntent(SearchIntent.AddToHistory(queryStr))
                        toSearchResPage(queryStr)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            isSubmitButtonEnabled = true //右边是否展示搜索图标
            val field = javaClass.getDeclaredField("mGoButton")
            field.run {
                isAccessible = true
                val mGoButton = get(searchView) as ImageView
                mGoButton.setImageResource(R.drawable.ic_search)
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setMenu() {
        setHasOptionsMenu(true)
        mToolBar.run {
            //设置menu 关键代码
            mActivity.setSupportActionBar(this)
            initClose {
                nav().navigateUp()
            }
        }
    }

    private fun toSearchResPage(queryStr: String){
        nav().navigateAction(R.id.action_searchFragment_to_searchResultFragment,
            Bundle().apply {
                putString(SEARCH_KEY, queryStr)
            }
        )
    }

}