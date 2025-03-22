package com.example.insider

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


val searchRetrofit: Retrofit = Retrofit
    .Builder()
    .baseUrl("https://850f-115-243-103-7.ngrok-free.app")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val searchCall: BackendApiService = searchRetrofit.create(BackendApiService::class.java)

interface BackendApiService {
    @POST("/search")
    suspend fun fetchSearchResult(@Body request: SearchQuery): List<Article>
}