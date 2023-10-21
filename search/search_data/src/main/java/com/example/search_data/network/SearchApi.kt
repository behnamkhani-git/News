package com.example.search_data.network

import com.example.search_data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SearchApi {

    // https://newsapi.org/v2/everything?q=apple&from=2023-10-20&to=2023-10-20&sortBy=popularity&apiKey=1bdf1f939d534eafa411b664e6beb9aa

    @GET("everything")
    suspend fun getSearchArticles(@QueryMap map: MutableMap<String, String>): NewsResponse

}