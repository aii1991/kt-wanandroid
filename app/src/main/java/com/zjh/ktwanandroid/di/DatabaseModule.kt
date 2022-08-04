package com.zjh.ktwanandroid.di

import android.app.Application
import androidx.room.Room
import com.zjh.ktwanandroid.data.datasource.local.AppDatabase
import com.zjh.ktwanandroid.data.datasource.local.dao.ArticleDao
import com.zjh.ktwanandroid.data.datasource.local.dao.RemoteKeyDao
import com.zjh.ktwanandroid.data.datasource.local.dao.UserInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author zjh
 * 2022/5/26
 */
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase{
        return Room.databaseBuilder(application,AppDatabase::class.java,AppDatabase.DB_NAME).allowMainThreadQueries().build()
    }

    @Provides
    internal fun provideArticleDao(appDatabase: AppDatabase): ArticleDao{
        return appDatabase.articleDao
    }

    @Provides
    internal fun provideRemoteKeyDao(appDatabase: AppDatabase): RemoteKeyDao{
        return appDatabase.remoteKeyDao
    }

    @Provides
    internal fun provideUserInfoDao(appDatabase: AppDatabase): UserInfoDao{
        return appDatabase.userInfoDao
    }
}