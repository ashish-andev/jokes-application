package com.ashish.myapplication.di

import android.content.Context
import com.ashish.myapplication.BuildConfig
import com.ashish.myapplication.data.source.remote.ApiService
import com.ashish.myapplication.domain.repository.JokesRepository
import com.ashish.myapplication.domain.usecase.GetJokeFromNetworkUseCase
import com.ashish.myapplication.data.repository.JokesRepositoryImpl
import com.ashish.myapplication.data.source.local.db.AppDatabase
import com.ashish.myapplication.domain.usecase.AddJokesToCacheUseCase
import com.ashish.myapplication.domain.usecase.GetCachedJokesUseCase
import com.ashish.myapplication.utils.NetworkConnectivity
import com.ashish.myapplication.utils.NetworkConnectivityImpl
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val READ_TIMEOUT = 30L  //In seconds
private const val CONNECT_TIMEOUT = 30L  //In seconds

val NetworkModule = module {

    single { createService(get()) }

    single { createDatabase(get()) }

    single { createRetrofit(get(), BuildConfig.BASE_URL) }

    single { createOkHttpClient() }

    single { MoshiConverterFactory.create() }

    single { Moshi.Builder().build() }

}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create()).build()
}

fun createService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun createDatabase(context: Context): AppDatabase {
    return AppDatabase.getInstance(context)
}

fun createJokesRepository(apiService: ApiService, appDatabase: AppDatabase): JokesRepository {
    return JokesRepositoryImpl(apiService, appDatabase)
}

fun createGetJokeFromNetworkUseCase(postsRepository: JokesRepository): GetJokeFromNetworkUseCase {
    return GetJokeFromNetworkUseCase(postsRepository)
}

fun createAddJokesToCacheUseCase(postsRepository: JokesRepository): AddJokesToCacheUseCase {
    return AddJokesToCacheUseCase(postsRepository)
}

fun createGetCachedJokesUseCase(postsRepository: JokesRepository): GetCachedJokesUseCase {
    return GetCachedJokesUseCase(postsRepository)
}

fun createNetworkConnectivity(context: Context): NetworkConnectivity {
    return NetworkConnectivityImpl(context)
}
