package com.zjh.ktwanandroid.presentation.mine.register

/**
 * @author zjh
 * 2022/8/2
 */
sealed class RegisterIntent {
    data class InputUserName(val userName:String): RegisterIntent()
    object ClearUserName: RegisterIntent()
    data class InputPwd(val pwd:String): RegisterIntent()
    data class ShowPwd(val isShow:Boolean): RegisterIntent()
    data class InputPwdConfirm(val pwd:String): RegisterIntent()
    data class ShowPwdConfirm(val isShow:Boolean): RegisterIntent()
    object Register: RegisterIntent()
}