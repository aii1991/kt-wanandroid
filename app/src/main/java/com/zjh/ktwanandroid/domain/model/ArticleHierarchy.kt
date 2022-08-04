package com.zjh.ktwanandroid.domain.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 体系数据
 * @author zjh
 * 2022/7/20
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ArticleHierarchy(var children: ArrayList<ArticleCategory>,
                            var courseId: Int,
                            var id: Int,
                            var name: String,
                            var order: Int,
                            var parentChapterId: Int,
                            var userControlSetTop: Boolean,
                            var visible: Int) : Parcelable
