package com.example.lab3_ex1

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService{
    @GET("v2/everything")
    suspend fun getArticles(
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String,
    ):NewsResponse
}