package com.example.seekho.di

import com.example.seekho.data.network.apiService.APIService
import com.example.seekho.data.network.apiService.DataApi
import com.example.seekho.utils.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

    @Provides
    fun provideAPIService(retrofit: Retrofit) : APIService {
        return APIService(retrofit = retrofit)
    }

    @Provides
    fun provideAnimeAPI(apiService: APIService) : DataApi {
        return apiService.createAPI(DataApi::class.java)
    }
}