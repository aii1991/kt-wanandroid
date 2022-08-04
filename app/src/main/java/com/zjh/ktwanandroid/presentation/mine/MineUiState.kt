package com.zjh.ktwanandroid.presentation.mine

data class MineUiState(val isLogin: Boolean = false,
                       val isRefresh: Boolean = false,
                       val avatarUrl: String = "",
                       val nickName: String = "",
                       val userId: Long = 0,
                       val ranking: Int = 0,
                       val integral: Int = 0)