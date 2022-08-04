package com.zjh.ktwanandroid.di

import com.google.gson.Gson
import com.zjh.ktwanandroid.AppCfg
import com.zjh.ktwanandroid.app.network.NetworkApi
import com.zjh.ktwanandroid.data.datasource.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author zjh
 * 2022/5/31
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): RemoteDataSource {
        return NetworkApi.INSTANCE.getApi(RemoteDataSource::class.java,AppCfg.SERVER_URL)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }
}