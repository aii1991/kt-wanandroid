package com.zjh.ktwanandroid.presentation.mine.register

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
class RegisterVM @Inject constructor(private val mineUseCase: MineUseCase) : BaseMVIVM<RegisterUiState, RegisterIntent>() {
    var mUiState = getUiState().asStateFlow()
    private fun reqRegister() = launch(true){
        getUiState().value.let {
            mineUseCase.register(it.userName, it.pwd, it.pwdConfirm)
            setData {uiState-> uiState.copy(isRegisterSuccess = true) }
        }
    }

    override fun createUiState(): RegisterUiState {
       return RegisterUiState()
    }

    override suspend fun handleIntent(sharedFlow: MutableSharedFlow<RegisterIntent>) {
        sharedFlow.collectLatest {
            when(it){
                is RegisterIntent.InputUserName->{
                    setData{ uiState-> uiState.copy(userName = it.userName) }
                }
                is RegisterIntent.ClearUserName->{
                    setData{ uiState-> uiState.copy(userName = "") }
                }
                is RegisterIntent.InputPwd->{
                    setData{ uiState-> uiState.copy(pwd = it.pwd) }
                }
                is RegisterIntent.ShowPwd->{
                    setData{ uiState-> uiState.copy(isShowPwd = it.isShow) }
                }
                is RegisterIntent.InputPwdConfirm->{
                    setData{ uiState-> uiState.copy(pwdConfirm = it.pwd) }
                }
                is RegisterIntent.ShowPwdConfirm->{
                    setData{ uiState-> uiState.copy(isShowConfirmPwd = it.isShow) }
                }
                is RegisterIntent.Register->{
                    reqRegister()
                }
            }
        }
    }

}

