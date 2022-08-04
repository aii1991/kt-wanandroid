package com.zjh.ktwanandroid.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author zjh
 * 2022/5/27
 */
@Parcelize
data class Banner(var desc: String = "",
                  var id: Int = 0,
                  var imagePath: String = "",
                  var isVisible: Int = 0,
                  var order: Int = 0,
                  var title: String = "",
                  var type: Int = 0,
                  var url: String = "") : Parcelable
