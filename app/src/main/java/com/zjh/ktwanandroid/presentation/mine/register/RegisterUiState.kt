package com.zjh.ktwanandroid.presentation.mine.register

data class RegisterUiState( val userName:String="",
                            val pwd:String="",
                            val pwdConfirm:String="",
                            val isShowPwd:Boolean = false,
                            val isShowConfirmPwd:Boolean = false,
                            val isRegisterSuccess:Boolean = false)