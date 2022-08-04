package com.zjh.ktwanandroid.presentation.mine.login

data class LoginUiState(
    val userName:String="",
    val pwd:String="",
    val isShowPwd:Boolean = false,
    val isLoginSuccess:Boolean=false)