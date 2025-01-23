package com.example.seekho.domain.core


data class Result<T>(val status : FetchStatus, val data: T?)