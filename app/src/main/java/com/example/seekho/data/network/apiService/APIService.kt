package com.example.seekho.data.network.apiService

import retrofit2.Retrofit
import javax.inject.Inject

class APIService @Inject constructor(private val retrofit: Retrofit) {
    fun <T> createAPI(klass: Class<T>): T {
        return retrofit.create(klass)
    }
}