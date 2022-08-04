package com.zjh.ktwanandroid.di

import com.zjh.ktwanandroid.data.datasource.local.AppDatabase
import com.zjh.ktwanandroid.data.datasource.remote.RemoteDataSource
import com.zjh.ktwanandroid.data.repository.imp.*
import com.zjh.ktwanandroid.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author zjh
 * 2022/5/31
 */
@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    internal fun provideHomeRepository(remoteDataSource: RemoteDataSource, database: AppDatabase): HomeRepository {
        return HomeRepositoryImp(remoteDataSource, database)
    }

    @Provides
    internal fun provideProjectRepository(remoteDataSource: RemoteDataSource, database: AppDatabase): ProjectRepository{
        return ProjectRepositoryImp(remoteDataSource, database)
    }

    @Provides
    internal fun provideMineRepository(remoteDataSource: RemoteDataSource, database: AppDatabase): MineRepository{
        return MineRepositoryImp(remoteDataSource, database)
    }

    @Provides
    internal fun providePublicNumberRepository(remoteDataSource: RemoteDataSource, database: AppDatabase): PublicNumberRepository{
        return PublicNumberRepositoryImp(remoteDataSource, database)
    }

    @Provides
    internal fun provideTreeRepository(remoteDataSource: RemoteDataSource, database: AppDatabase): TreeRepository {
        return TreeRepositoryImp(remoteDataSource,database)
    }

    @Provides
    internal fun provideSearchRepository(remoteDataSource: RemoteDataSource, database: AppDatabase): SearchRepository{
        return SearchRepositoryImp(remoteDataSource,database)
    }

}