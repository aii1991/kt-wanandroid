package com.zjh.ktwanandroid.domain.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 文章
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class Article(
    var articleType: Int = -1,
    var categoryId: Int = -1,
    var apkLink: String? = "",
    var author: String? = "",//作者
    var chapterId: Int = 0,
    var chapterName: String? = "",
    var collect: Boolean = false,//是否收藏
    var courseId: Int = 0,
    var desc: String? = "",
    var envelopePic: String? = "",
    var fresh: Boolean = false,
    var id: Int,
    var link: String? = "",
    var niceDate: String? = "",
    var origin: String? = "",
    var prefix: String? = "",
    var projectLink: String? = "",
    var publishTime: Long = 0,
    var superChapterId: Int? = 0,
    var superChapterName: String? = "",
    var shareUser: String? = "",
    var tags: List<Tag>? = listOf(),
    var title: String? = "",
    var type: Int? = 0,
    var userId: Int = 0,
    var visible: Int = 0,
    var zan: Int = 0) : Parcelable
