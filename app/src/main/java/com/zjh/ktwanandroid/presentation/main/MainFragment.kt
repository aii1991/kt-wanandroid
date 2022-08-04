package com.zjh.ktwanandroid.presentation.main

import android.os.Bundle
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseFragment
import com.zjh.ktwanandroid.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import init
import initMain
import interceptLongClick

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

@AndroidEntryPoint
class MainFragment : BaseFragment<BaseViewModel, FragmentMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.mainViewPager.initMain(this)
        mDatabind.mainBottomNav.init {
            when (it){
                R.id.menu_main -> mDatabind.mainViewPager.setCurrentItem(0,false)
                R.id.menu_project -> mDatabind.mainViewPager.setCurrentItem(1, false)
                R.id.menu_tree -> mDatabind.mainViewPager.setCurrentItem(2, false)
                R.id.menu_public -> mDatabind.mainViewPager.setCurrentItem(3, false)
                R.id.menu_mine -> mDatabind.mainViewPager.setCurrentItem(4, false)
            }
        }
        mDatabind.mainBottomNav.interceptLongClick(R.id.menu_main,R.id.menu_project,R.id.menu_tree,R.id.menu_project,R.id.menu_mine)
    }
}