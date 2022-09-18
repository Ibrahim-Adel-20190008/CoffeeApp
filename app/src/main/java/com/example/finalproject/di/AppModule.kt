package com.example.finalproject.di

import com.example.finalproject.data.api.ServicesApi
import com.example.finalproject.main.DefaultMainRepository
import com.example.finalproject.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL ="https://coffee-menu123.herokuapp.com"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providerServicesApi() : ServicesApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ServicesApi::class.java)

    @Singleton
    @Provides
    fun providerMainRepository(api: ServicesApi):
            MainRepository = DefaultMainRepository(api)

}