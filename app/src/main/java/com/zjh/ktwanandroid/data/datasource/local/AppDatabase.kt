package com.zjh.ktwanandroid.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zjh.ktwanandroid.data.datasource.local.dao.ArticleDao
import com.zjh.ktwanandroid.data.datasource.local.dao.RemoteKeyDao
import com.zjh.ktwanandroid.data.datasource.local.dao.SearchDao
import com.zjh.ktwanandroid.data.datasource.local.dao.UserInfoDao
import com.zjh.ktwanandroid.data.datasource.local.entity.*

/**
 * @author zjh
 * 2022/5/26
 */
@Database(entities = [UserInfoEntity::class,ArticleEntity::class, TagEntity::class, RemoteKeyEntity::class, SearchEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract val userInfoDao: UserInfoDao
    abstract val articleDao: ArticleDao
    abstract val remoteKeyDao: RemoteKeyDao
    abstract val searchDao: SearchDao

    companion object{
        const val DB_NAME = "wan_android.db"
    }
}