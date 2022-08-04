package com.zjh.ktwanandroid.domain.model
import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　: 账户信息
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class UserInfo(var admin: Boolean = false,
                    var chapterTops: List<String> = listOf(),
                    var collectIds: MutableList<String> = mutableListOf(),
                    var email: String="",
                    var icon: String="",
                    var id: Long = 0,
                    var nickname: String="",
                    var password: String="",
                    var token: String="",
                    var type: Int =0,
                    var username: String="",
                    var ranking: Int=0,
                    var integral: Int=0,
                    var avatarUrl: String = ""
                    ) : Parcelable {
}
