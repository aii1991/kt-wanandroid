package com.zjh.ktwanandroid.presentation.project
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import bindViewPager2
import com.zjh.ktwanandroid.app.base.BaseMVIFragment
import com.zjh.ktwanandroid.databinding.FragmentProjectBinding
import com.zjh.ktwanandroid.presentation.ArticleListIntent
import dagger.hilt.android.AndroidEntryPoint
import init
import kotlinx.coroutines.CoroutineScope
import net.lucode.hackware.magicindicator.MagicIndicator

/**
 * 项目文章fragment
 */
@AndroidEntryPoint
class ProjectFragment : BaseMVIFragment<ProjectVM, FragmentProjectBinding>() {
    //fragment集合
    private val mFragments: ArrayList<Fragment> = arrayListOf()
    //标题集合
    private val mTitleList: ArrayList<String> = arrayListOf()

    private lateinit var mViewPager: ViewPager2
    private lateinit var mMagicIndicator: MagicIndicator

    override fun setupView(savedInstanceState: Bundle?) {
        mDatabind.includeViewpager.let{
            mViewPager = it.viewPager.apply {
                init(this@ProjectFragment,mFragments)
            }
            mMagicIndicator = it.magicIndicator.apply {
                bindViewPager2(it.viewPager,mTitleList)
            }
        }
    }

    override fun lazyLoadData() {
        mViewModel.dispatchIntent(ArticleListIntent.LoadData)
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope) {
        mViewModel.mUiState.collect{
            updateUi(it)
        }
    }

    private fun updateUi(projectUiState: ProjectUiState){
        projectUiState.articleCategories?.let { articleCategories->
            mTitleList.clear()
            mTitleList.add("最新项目")
            mTitleList.addAll(articleCategories.map { it.name })
            mMagicIndicator.navigator.notifyDataSetChanged()

            mFragments.clear()
            mFragments.add(ProjectSubFragment.newInstance(0))
            articleCategories.forEach {articleCategory->
                mFragments.add(ProjectSubFragment.newInstance(articleCategory.id))
            }

            mViewPager.adapter?.notifyItemRangeChanged(0,mFragments.size)
            mViewPager.offscreenPageLimit = mFragments.size
        }
    }

}