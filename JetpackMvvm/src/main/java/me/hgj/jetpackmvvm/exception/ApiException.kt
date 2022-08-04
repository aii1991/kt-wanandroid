package me.hgj.jetpackmvvm.exception

/**
 * @author zjh
 * 2022/6/20
 */
sealed class ApiException {
    class NotLoginException(val code: Int = 1001,val msg: String = "please login"): Exception(msg)
    class ReqErrException(val code:Int = 1002, val msg: String) : Exception(msg)
}

