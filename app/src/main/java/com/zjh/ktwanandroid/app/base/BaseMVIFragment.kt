package com.zjh.ktwanandroid.app.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import me.hgj.jetpackmvvm.ext.launchRepeatOnLifecycle

/**
 * @author zjh
 * 2022/6/23
 */
abstract class BaseMVIFragment<VM: BaseMVIVM<*,*>,DB: ViewBinding> : BaseFragment<VM,DB>() {
    override fun initView(savedInstanceState: Bundle?) {
        getArguments(arguments)
        setupView(savedInstanceState)
        bindListener()
        bindUiState()
    }

    private fun bindUiState(){
        launchRepeatOnLifecycle{
            observeUiState(it)
        }
        launchRepeatOnLifecycle{
            observeRootUiState()
        }
    }

    open fun getArguments(args: Bundle?){}
    abstract fun setupView(savedInstanceState: Bundle?)
    open fun bindListener(){}
    abstract suspend fun observeUiState(coroutineScope: CoroutineScope)

    private suspend fun observeRootUiState(){
        mViewModel.mRootUiState.collectLatest {
            if(it.errMsg.isNotEmpty()){
                showErrMsgToUser(it.errMsg)
            }
            if(it.isShowLoading) showLoading() else dismissLoading()
        }
    }

}