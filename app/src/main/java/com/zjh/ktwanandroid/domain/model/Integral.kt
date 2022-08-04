package com.zjh.ktwanandroid.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author zjh
 * 2022/6/17
 */
@Parcelize
data class Integral(var coinCount: Int,//当前积分
var rank: Int,
var userId: Int,
var username: String) : Parcelable