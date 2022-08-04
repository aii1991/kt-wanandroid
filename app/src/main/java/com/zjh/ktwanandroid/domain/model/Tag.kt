package com.zjh.ktwanandroid.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author zjh
 * 2022/5/31
 */
@Parcelize
data class Tag(var name:String, var url:String): Parcelable
