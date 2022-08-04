package com.zjh.ktwanandroid.presentation.mine

import com.zjh.ktwanandroid.app.base.BaseMVIVM
import com.zjh.ktwanandroid.domain.usecase.MineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * @author zjh
 * 2022/6/17
 */
@HiltViewModel
class MineVM @Inject constructor(private val mineUseCase: MineUseCase) : BaseMVIVM<MineUiState, MineIntent>() {
    var mUiState = getUiState().asStateFlow()
    private fun loadUserIntegral() = launch(onStart = {
        setData {  it.copy(isRefresh = true)  }
    }, onCompletion = {
        setData {  it.copy(isRefresh = false)  }
    }) {
        val userIntegral = mineUseCase.getUserIntegral()
        setData {
            it.copy(isLogin=true, isRefresh = false, ranking=userIntegral.rank,integral=userIntegral.coinCount)
        }
    }


    private fun loadUserInfo() = launch {
        val userInfo = mineUseCase.getUserInfo()
        setData {
            it.copy(isLogin=true, avatarUrl = userInfo.avatarUrl, nickName=userInfo.nickname,userId=userInfo.id,ranking=userInfo.ranking,integral=userInfo.integral)
        }
    }

    override fun createUiState(): MineUiState {
        return MineUiState()
    }

    override suspend fun handleIntent(sharedFlow: MutableSharedFlow<MineIntent>) {
        sharedFlow.collectLatest {
            when(it){
                is MineIntent.LoadData-> {
                    loadUserInfo()
                    loadUserIntegral()
                }
            }
        }
    }
}



