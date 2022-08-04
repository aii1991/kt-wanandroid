package com.zjh.ktwanandroid.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.zjh.ktwanandroid.data.datasource.local.dao.converter.TagTypeConverter

/**
 * @author zjh
 * 2022/5/31
 */
@Entity(tableName = "t_article", primaryKeys = ["id","articleType","categoryId"])
@TypeConverters(TagTypeConverter::class)
data class ArticleEntity(
                        var articleType: Int,
                        var categoryId: Int,
                        var apkLink: String?,
                         var author: String?,//作者
                         var chapterId: Int,
                         var chapterName: String?,
                         var collect: Boolean = false,//是否收藏
                         var courseId: Int,
                         var desc: String?,
                         var envelopePic: String?,
                         var fresh: Boolean = false,
                         var id: Int,
                         var link: String?,
                         var niceDate: String?,
                         var origin: String?,
                         var prefix: String?,
                         var projectLink: String?,
                         var publishTime: Long,
                         var superChapterId: Int?,
                         var superChapterName: String?,
                         var shareUser: String?,
                         var tags: List<TagEntity> = listOf(),
                         var title: String?,
                         var type: Int?,
                         var userId: Int,
                         var visible: Int,
                         var zan: Int)
