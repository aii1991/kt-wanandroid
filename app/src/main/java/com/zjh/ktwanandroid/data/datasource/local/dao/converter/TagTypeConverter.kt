package com.zjh.ktwanandroid.data.datasource.local.dao.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zjh.ktwanandroid.data.datasource.local.entity.TagEntity

/**
 * @author zjh
 * 2022/5/31
 * List<TagEntity>类型转换
 */
class TagTypeConverter {
    @TypeConverter
    fun stringToObject(value: String): List<TagEntity>{
        val listType = object : TypeToken<List<TagEntity>>(){}.type
        return Gson().fromJson(value,listType)
    }
    @TypeConverter
    fun objectToString(tags: List<TagEntity>): String{
        return Gson().toJson(tags)
    }
}