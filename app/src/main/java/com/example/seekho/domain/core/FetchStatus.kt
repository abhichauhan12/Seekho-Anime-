package com.example.seekho.domain.core

sealed class FetchStatus{
    data object UNDEFINED : FetchStatus()
    data object FETCHING : FetchStatus()
    data object FETCHED : FetchStatus()
    class FAILURE(val message: String?) : FetchStatus()
}
