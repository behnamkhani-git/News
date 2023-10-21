package com.example.news_data.repository


import com.example.news_data.mapper.toDomainArticle
import com.example.news_data.network.NewsApiService
import com.example.news_domain.model.Article
import com.example.news_domain.repository.NewsRepository

class NewsRepoImpl(private val newsApiService: NewsApiService) : NewsRepository {
    override suspend fun getNewsArticle(): List<Article> {                                              // The getNewsArticle function is defined with the suspend modifier, indicating that it is a suspending function that can be called from a coroutine. Inside the getNewsArticle function, it uses the injected newsApiService to call the getNewsArticles function, passing the country parameter as "us". This function is assumed to make an API call to retrieve news articles.

        // The result of newsApiService.getNewsArticles is assumed to be a data model object with a property named articles. The code uses the map function to transform each item in the articles list to a domain model object of type Article by calling the toDomainArticle() function on each item.
        return newsApiService.getNewsArticles(country = "us").articles.map {
            it.toDomainArticle()                                                                        // Finally, the transformed list of domain model articles is returned from the getNewsArticle function.
        }
    }

}