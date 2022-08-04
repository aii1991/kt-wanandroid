package com.zjh.ktwanandroid.presentation.mine.login

import com.zjh.ktwanandroid.app.base.BaseMVIVM
import com.zjh.ktwanandroid.domain.usecase.MineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * @author zjh
 * 2022/6/20
 */
@HiltViewModel
class LoginVM @Inject constructor(private val mineUseCase: MineUseCase): BaseMVIVM<LoginUiState, LoginIntent>(){
    var mUiState = getUiState().asStateFlow()

    private fun reqLogin() = launch(true){
        mineUseCase.login(getUiState().value.userName, getUiState().value.pwd)
        setData { it.copy(isLoginSuccess = true) }
    }

    override fun createUiState(): LoginUiState {
        return LoginUiState()
    }

    override suspend fun handleIntent(sharedFlow: MutableSharedFlow<LoginIntent>) {
        sharedFlow.collectLatest {
            when(it){
                is LoginIntent.InputUserName->{
                    setData{ uiState-> uiState.copy(userName = it.userName) }
                }
                is LoginIntent.ClearUserName->{
                    setData{ uiState-> uiState.copy(userName = "") }
                }
                is LoginIntent.InputPwd->{
                    setData{ uiState-> uiState.copy(pwd = it.pwd) }
                }
                is LoginIntent.ShowPwd->{
                    setData{ uiState-> uiState.copy(isShowPwd = it.isShow) }
                }
                is LoginIntent.Login->{
                    reqLogin()
                }
            }
        }
    }
}

