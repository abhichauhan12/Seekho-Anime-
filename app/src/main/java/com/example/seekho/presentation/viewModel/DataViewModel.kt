package com.example.seekho.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seekho.data.network.apiResponse.Data
import com.example.seekho.domain.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DataViewModel  @Inject constructor(
    private val networkRepository: NetworkRepository
): ViewModel() {

    private val _apiAnimeData = MutableStateFlow<List<Data>>(value = emptyList())
    val animeData = _apiAnimeData.asStateFlow()

    private val _apiAnimeDetailData = MutableStateFlow<Data?>(value = null)
    val animeDetailData = _apiAnimeDetailData.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val response = networkRepository.getTopAnimeData()

                response.data?.let { apiResponse ->
                    Log.d("ViewModel", "Fetched Data: ${apiResponse.data}")
                    _apiAnimeData.value = apiResponse.data

                }

            }
        }
    }

    fun getAnimeDetailData(animeId:Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val response = networkRepository.getDetailAnimeData(animeId)

                response.data?.let { apiResponse ->
                    Log.d("ViewModel", "Fetched Data: ${apiResponse.data}")
                    _apiAnimeDetailData.value = apiResponse.data

                }

            }
        }
    }


}