package com.example.seekho.domain.repository

import android.util.Log
import com.example.seekho.data.network.apiResponse.DetailsApiResponse
import com.example.seekho.data.network.apiResponse.TopApiResponse
import com.example.seekho.data.network.apiService.DataApi
import com.example.seekho.domain.core.FetchStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.seekho.domain.core.Result
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val dataApi: DataApi
) {

    suspend fun getTopAnimeData() :Result<TopApiResponse>{
        return withContext(Dispatchers.IO){
            try {
                val response = dataApi.getAnime()
                val responseBody =response.body()
                Log.d("API Success", "Response: $responseBody")

                if (response.isSuccessful && responseBody != null){
                    Result(FetchStatus.FETCHED, responseBody)
                } else
                    Result(FetchStatus.FAILURE("Failed to fetch data: ${response.code()}"), null)
            }catch (e: Exception){
                Log.d("API Error", "Failed to fetch data: ${e.message}")
                Result(FetchStatus.FAILURE(e.message), null)
            }
        }
    }

    suspend fun getDetailAnimeData(animeId: Int) :Result<DetailsApiResponse>{
        return withContext(Dispatchers.IO){
            try {
                val response = dataApi.getAnimeDetails(animeId)
                val responseBody =response.body()
                Log.d("Detail API Success ", "Response: $responseBody")

                if (response.isSuccessful && responseBody != null){
                    Result(FetchStatus.FETCHED, responseBody)
                } else
                    Result(FetchStatus.FAILURE("Failed to fetch data: ${response.code()}"), null)
            }catch (e: Exception){
                Log.d("API Error", "Failed to fetch data: ${e.message}")
                Result(FetchStatus.FAILURE(e.message), null)
            }
        }
    }



}