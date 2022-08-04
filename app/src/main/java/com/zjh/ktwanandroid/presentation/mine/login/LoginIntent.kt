package com.zjh.ktwanandroid.presentation.mine.login

/**
 * @author zjh
 * 2022/8/2
 */
sealed class LoginIntent {
    data class InputUserName(val userName:String): LoginIntent()
    object ClearUserName: LoginIntent()
    data class InputPwd(val pwd:String): LoginIntent()
    data class ShowPwd(val isShow:Boolean): LoginIntent()
    object Login: LoginIntent()
}