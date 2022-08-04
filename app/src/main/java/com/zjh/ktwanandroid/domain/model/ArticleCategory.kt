package com.zjh.ktwanandroid.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author zjh
 * 2022/7/8
 */
@Parcelize
data class ArticleCategory(var children: List<String> = listOf(),
                           var courseId: Int = 0,
                           var id: Int = 0,
                           var name: String = "",
                           var order: Int = 0,
                           var parentChapterId: Int = 0,
                           var userControlSetTop: Boolean = false,
                           var visible: Int = 0): Parcelable
