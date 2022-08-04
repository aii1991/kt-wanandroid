package com.zjh.ktwanandroid.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author zjh
 * 2022/6/16
 */
@Parcelize
data class CollectArticle(var chapterId: Int,
                          var author: String,
                          var chapterName: String,
                          var courseId: Int,
                          var desc: String,
                          var envelopePic: String,
                          var id: Int,
                          var link: String,
                          var niceDate: String,
                          var origin: String,
                          var originId: Int,
                          var publishTime: Long,
                          var title: String,
                          var userId: Int,
                          var visible: Int,
                          var zan: Int): Parcelable

fun CollectArticle.toArticle(): Article = Article(chapterId = chapterId,
    author = author,
    chapterName = chapterName,
    courseId = courseId,
    desc = desc,
    envelopePic = envelopePic,
    id = originId,
    link = link,
    niceDate = niceDate,
    origin = origin,
    publishTime = publishTime,
    title = title,
    userId = userId,
    visible = visible,
    zan = zan,
    collect = true)
