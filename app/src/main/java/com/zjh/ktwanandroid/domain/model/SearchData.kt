package com.zjh.ktwanandroid.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author zjh
 * 2022/7/25
 */
@Parcelize
data class SearchData(var id: Int = 0,
                      var link: String = "",
                      var name: String = "",
                      var order: Int = 0,
                      var visible: Int = 0) : Parcelable