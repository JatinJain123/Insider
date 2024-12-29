package com.example.insider

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val retrofit: Retrofit = Retrofit
    .Builder()
    .baseUrl("https://newsapi.org/v2/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val call: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {
    // to get top headlines from countries
    @GET("top-headlines")
    suspend fun fetchCountryTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse


    // to get everything data for a query
    @GET("everything")
    suspend fun fetchEverythingData(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse


    // to get data from popular sources
    @GET("top-headlines")
    suspend fun fetchFromSources(
        @Query("sources") source: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}