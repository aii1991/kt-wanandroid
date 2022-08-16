package com.zjh.ktwanandroid.presentation.mine

import android.os.Bundle
import coil.load
import com.zjh.ktwanandroid.R
import com.zjh.ktwanandroid.app.base.BaseMVIFragment
import com.zjh.ktwanandroid.app.ext.jumpByLogin
import com.zjh.ktwanandroid.databinding.FragmentMimeBinding
import com.zjh.ktwanandroid.domain.model.Banner
import com.zjh.ktwanandroid.presentation.webview.WebFragment.ParamKey.BANNER_KEY
import dagger.hilt.android.AndroidEntryPoint
import initColorScheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

@AndroidEntryPoint
class MimeFragment : BaseMVIFragment<MineVM, FragmentMimeBinding>() {

    override fun setupView(savedInstanceState: Bundle?){
        mDatabind.mineSwipe.initColorScheme()
    }

    override fun bindListener(){
        mDatabind.apply {
            mineSwipe.setOnRefreshListener {
                getData()
            }
            llMyCollect.setOnClickListener{
                nav().jumpByLogin {
                    it.navigateAction(R.id.action_main_fragment_to_myCollectMainFragment)
                }
            }
            llOpenSourceUrl.setOnClickListener {
                nav().navigateAction(R.id.action_main_fragment_to_webFragment,Bundle().apply {
                    putParcelable(BANNER_KEY,Banner(title = "玩Android网站", url = "https://www.wanandroid.com/"))
                })
            }
            llSetting.setOnClickListener {
                nav().navigateAction(R.id.action_main_fragment_to_settingFragment)
            }
            mineLinear.setOnClickListener{
                nav().jumpByLogin {}
            }
        }
    }

    override suspend fun observeUiState(coroutineScope: CoroutineScope){

        coroutineScope.launch {
            mViewModel.mUiState.collect {
                mDatabind.apply {
                    if (it.isLogin){
                        if(it.avatarUrl.isNotEmpty()){
                            ivAvatar.load(it.avatarUrl)
                        }
                        mineName.text = it.nickName
                        mineInfo.text = "id:${it.userId} 排名:${it.ranking}"
                        mineIntegral.text = it.integral.toString()
                    }else{
                        mineName.text = "请先登录~"
                    }
                    mineSwipe.isRefreshing = it.isRefresh
                }
            }
        }
    }

    override fun lazyLoadData() {
        getData()
    }

    override fun onResume() {
        super.onResume()
        if(!isFirstLoad()){
            getData()
        }
    }

    private fun getData(){
        mViewModel.dispatchIntent(MineIntent.LoadData)
    }
}