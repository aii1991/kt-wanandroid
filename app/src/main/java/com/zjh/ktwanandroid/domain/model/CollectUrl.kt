package com.zjh.ktwanandroid.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author zjh
 * 2022/7/29
 */
@Parcelize
data class CollectUrl(var icon: String,
                      var id: Int,
                      var link: String,
                      var name: String,
                      var order: Int,
                      var userId: Int,
                      var visible: Int) : Parcelable
