package com.vp.loyaltypartner.di.module

import com.vp.loyaltypartner.BuildConfig
import com.vp.loyaltypartner.data.remote.ApiHelper
import com.vp.loyaltypartner.data.remote.ApiHelperImpl
import com.vp.loyaltypartner.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

  @Provides
  fun provideBaseUrl() = BuildConfig.BASE_URL

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    OkHttpClient.Builder()
      .connectTimeout(5, TimeUnit.MINUTES)
      .writeTimeout(5, TimeUnit.MINUTES) // write timeout
      .readTimeout(5, TimeUnit.MINUTES)
      .addInterceptor(loggingInterceptor)
      .build()
  } else {
    OkHttpClient.Builder()
      .connectTimeout(5, TimeUnit.MINUTES)
      .writeTimeout(5, TimeUnit.MINUTES) // write timeout
      .readTimeout(5, TimeUnit.MINUTES) // read timeout
      .build()
  }



  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
    Retrofit.Builder()
      .addConverterFactory(MoshiConverterFactory.create())
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .build()

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

  @Provides
  @Singleton
  fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}