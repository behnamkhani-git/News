package com.example.news_data.network

import android.provider.SyncStateContract
import com.example.common_utils.Constants
import com.example.news_data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    // https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=1bdf1f939d534eafa411b664e6beb9aa

    @GET("top-headlines")
    suspend fun getNewsArticles(
        @Query("country") country: String,
        @Query("category") category: String = Constants.CATEGORY,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): NewsResponse
}