package com.zjh.ktwanandroid.domain.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 导航数据
 * @author zjh
 * 2022/7/20
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ArticleNavigation(var articles: ArrayList<Article>,
                             var cid: Int,
                             var name: String) : Parcelable
