package com.zjh.ktwanandroid.presentation.tree

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import bindViewPager2
import com.zjh.ktwanandroid.KEY_ARTICLE_HIERARCHY
import com.zjh.ktwanandroid.KEY_TAB_INDEX
import com.zjh.ktwanandroid.app.base.BaseFragment
import com.zjh.ktwanandroid.databinding.FragmentHierarchyTabBinding
import com.zjh.ktwanandroid.domain.model.ArticleHierarchy
import dagger.hilt.android.AndroidEntryPoint
import init
import initClose
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.nav

@AndroidEntryPoint
class HierarchyTabFragment : BaseFragment<BaseViewModel, FragmentHierarchyTabBinding>() {
    lateinit var mArticleHierarchy: ArticleHierarchy
    private var tabIdx = 0
    private val mFragments: ArrayList<Fragment> = arrayListOf()

    companion object{
        fun newInstance(articleHierarchy: ArticleHierarchy,tabIdx: Int): Fragment{
            val fragment = HierarchyTabFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_ARTICLE_HIERARCHY,articleHierarchy)
                    putInt(KEY_TAB_INDEX,tabIdx)
                }
            }
            return fragment
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            mArticleHierarchy = it.getParcelable(KEY_ARTICLE_HIERARCHY)!!
            tabIdx = it.getInt(KEY_TAB_INDEX)
            mArticleHierarchy.children.forEach{ articleCategory->
                mFragments.add(HierarchyTabContentFragment.newInstance(articleCategory.id))
            }
        }

        mDatabind.includeToolbar.apply {
            toolbar.initClose(mArticleHierarchy.name) {
                nav().navigateUp()
            }
        }
        mDatabind.includeViewpager.apply {
            (magicIndicator.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.START
            viewPager.init(this@HierarchyTabFragment, fragments = mFragments)
            magicIndicator.bindViewPager2(viewPager,mArticleHierarchy.children.map{it.name})
            viewPager.offscreenPageLimit = mFragments.size
            viewPager.postDelayed({ viewPager.currentItem = tabIdx },100)
        }
    }

}