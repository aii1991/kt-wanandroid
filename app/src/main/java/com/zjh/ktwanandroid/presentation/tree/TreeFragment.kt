package com.zjh.ktwanandroid.presentation.tree

import android.os.Bundle
import androidx.fragment.app.Fragment
import bindViewPager2
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseFragment
import com.zjh.ktwanandroid.app.ext.jumpByLogin
import com.zjh.ktwanandroid.databinding.FragmentTreeBinding
import com.zjh.ktwanandroid.domain.model.enums.ArticleType
import dagger.hilt.android.AndroidEntryPoint
import init
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

@AndroidEntryPoint
class TreeFragment : BaseFragment<TreeVM, FragmentTreeBinding>() {
    private val mTitleList = arrayListOf("广场", "每日一问", "体系", "导航")
    private val mFragments: ArrayList<Fragment> = arrayListOf()

    init {
        mFragments.add(TreeSubListFragment.newInstance(ArticleType.TREE_SQUARE))
        mFragments.add(TreeSubListFragment.newInstance(ArticleType.TREE_ASK))
        mFragments.add(HierarchyFragment())
        mFragments.add(NavigationFragment())
    }

    override fun initView(savedInstanceState: Bundle?) {

        mDatabind.includeViewpager.let{
            it.includeViewpagerToolbar.run{
                inflateMenu(R.menu.tree_menu)
                setOnMenuItemClickListener{
                    menuItem->
                    when (menuItem.itemId) {
                        R.id.add -> {
                            nav().jumpByLogin {
                                nav().navigateAction(R.id.action_main_fragment_to_addArticleFragment)
                            }
                        }
                    }
                    true
                }
            }

            val mViewPager = it.viewPager.apply {
                init(this@TreeFragment,mFragments)
            }
            mViewPager.offscreenPageLimit = mFragments.size
            it.magicIndicator.apply {
                bindViewPager2(it.viewPager,mTitleList){
                    idx->
                    if(idx != 0){
                        it.includeViewpagerToolbar.menu.clear()
                    }else{
                        it.includeViewpagerToolbar.menu.hasVisibleItems().let {
                            flag->
                            if(!flag){
                                it.includeViewpagerToolbar.inflateMenu(R.menu.tree_menu)
                            }
                        }
                    }

                }
            }
        }
    }

    override fun lazyLoadData() {

    }
}