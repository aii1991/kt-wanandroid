package com.zjh.ktwanandroid.presentation.mine.mycollect

import android.os.Bundle
import androidx.fragment.app.Fragment
import bindViewPager2
import com.zjh.ktwanandroid.app.base.BaseFragment
import com.zjh.ktwanandroid.databinding.FragmentMyCollectMainBinding
import dagger.hilt.android.AndroidEntryPoint
import init
import initClose
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.nav

@AndroidEntryPoint
class MyCollectMainFragment : BaseFragment<BaseViewModel,FragmentMyCollectMainBinding>() {
    private val mTitleData = arrayListOf("文章","网址")
    private val mFragments: ArrayList<Fragment> = arrayListOf()

    init {
        mFragments.add(MyCollectListFragment())
        mFragments.add(MyCollectUrlListFragment())
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.apply {
            includeToolbar.toolbar.initClose {
                nav().navigateUp()
            }
            collectViewPager.init(this@MyCollectMainFragment, mFragments)
            collectMagicIndicator.bindViewPager2(collectViewPager,mTitleData)
        }
    }
}