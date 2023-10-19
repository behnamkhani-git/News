package com.example.news_domain.repository

import com.example.news_data.mapper.toDomainArticle
import com.example.news_data.network.NewsApiService
import com.example.news_domain.model.Article

class NewsRepoImpl(private val newsApiService: NewsApiService): NewsRepository {
    override suspend fun getNewsArticle(): List<Article> {
        return newsApiService.getNewsArticles(country = "us").articles.map {
            it.toDomainArticle()
        }
    }

}