package com.example.marvel.core.network.di

import com.example.marvel.BuildConfig
import com.example.marvel.core.network.interceptor.AuthorizationInterceptor
import com.example.marvel.core.network.services.MarvelServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MarvelServices {
        return retrofit.create(MarvelServices::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor,
    ): OkHttpClient {

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(httpLoggingInterceptor).addInterceptor(authorizationInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor {
        return AuthorizationInterceptor(BuildConfig.PRIVATE_KEY, BuildConfig.PUBLIC_KEY)
    }

}