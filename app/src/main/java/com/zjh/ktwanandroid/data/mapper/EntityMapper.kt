package com.zjh.ktwanandroid.data.mapper

import com.zjh.ktwanandroid.data.datasource.local.entity.ArticleEntity
import com.zjh.ktwanandroid.data.datasource.local.entity.SearchEntity
import com.zjh.ktwanandroid.data.datasource.local.entity.TagEntity
import com.zjh.ktwanandroid.data.datasource.local.entity.UserInfoEntity
import com.zjh.ktwanandroid.domain.model.Article
import com.zjh.ktwanandroid.domain.model.SearchData
import com.zjh.ktwanandroid.domain.model.Tag
import com.zjh.ktwanandroid.domain.model.UserInfo

/**
 * @author zjh
 * 2022/5/26
 */
fun UserInfo.toEntity() = UserInfoEntity(id,email,icon,nickname,type,username)

fun Article.toEntity() = ArticleEntity(articleType,categoryId,apkLink, author, chapterId, chapterName, collect,courseId, desc, envelopePic, fresh, id, link, niceDate,
origin, prefix, projectLink, publishTime, superChapterId, superChapterName, shareUser,
    tags?.map { TagEntity(name = it.name,url = it.url) } ?: listOf(), title, type, userId, visible, zan)

fun ArticleEntity.toArticle() = Article(articleType,categoryId,apkLink, author, chapterId, chapterName, collect,courseId, desc, envelopePic, fresh, id, link, niceDate,
    origin, prefix, projectLink, publishTime, superChapterId, superChapterName, shareUser, tags.map { Tag(name = it.name,url = it.url) }, title, type, userId, visible, zan)

fun SearchData.toEntity() = SearchEntity(name=name)

fun SearchEntity.toSearchData() = SearchData(name = name)