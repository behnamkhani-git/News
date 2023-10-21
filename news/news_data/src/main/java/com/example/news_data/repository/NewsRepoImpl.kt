package com.example.news_data.repository


import com.example.news_data.mapper.toDomainArticle
import com.example.news_data.network.NewsApiService
import com.example.news_data.room.NewsDAO
import com.example.news_domain.model.Article
import com.example.news_domain.repository.NewsRepository

/*
This code snippet demonstrates a function that retrieves news articles by first making an API call,
transforming the data, and storing it in the local data storage.
It provides fault tolerance by falling back to the local data storage in case of any exceptions.
 */
class NewsRepoImpl(private val newsApiService: NewsApiService, private val newsDAO: NewsDAO) :
    NewsRepository {
    override suspend fun getNewsArticle(): List<Article> {                                              // The getNewsArticle function is defined with the suspend modifier, indicating that it is a suspending function that can be called from a coroutine. Inside the getNewsArticle function, it uses the injected newsApiService to call the getNewsArticles function, passing the country parameter as "us". This function is assumed to make an API call to retrieve news articles.
        return try {
            // The result of newsApiService.getNewsArticles is assumed to be a data model object with a property named articles. The code uses the map function to transform each item in the articles list to a domain model object of type Article by calling the toDomainArticle() function on each item.
            val temp = newsApiService.getNewsArticles(country = "us").articles.map {
                it.toDomainArticle()
            }
            newsDAO.insertList(temp)
            newsDAO.getNewsArticle()
        } catch (e: Exception) {
            newsDAO.getNewsArticle()
        }
    }
}