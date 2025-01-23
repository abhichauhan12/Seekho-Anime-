package com.example.seekho.data.network.apiService

import com.example.seekho.data.network.apiResponse.DetailsApiResponse
import com.example.seekho.data.network.apiResponse.TopApiResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DataApi {
    @GET("top/anime")
    suspend fun getAnime(): Response<TopApiResponse>

    @GET("anime/{anime_id}")
    suspend fun getAnimeDetails(@Path("anime_id") animeId: Int) : Response<DetailsApiResponse>

}