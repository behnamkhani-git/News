package com.example.news_domain.use_case

import com.example.common_utils.Resource
import com.example.news_domain.model.Article
import com.example.news_domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
This class is responsible for fetching news articles from a newsRepository and returning the result as a Flow of Resource objects.
 */
class GetNewsArticleUseCase(private val newsRepository: NewsRepository) {                               // The GetNewsArticleUseCase class has a constructor that takes an instance of the NewsRepository. The NewsRepository is presumably an abstraction or interface that defines the methods for accessing news articles. You can use Hilt for dependency injection instead of constructor injection in the GetNewsArticleUseCase class.

    // The invoke function returns a Flow of Resource<List<Article>>. The Resource class is typically used to represent different states of data loading, such as loading, success, and error.
    // The invoke function is a special function in Kotlin that allows you to treat an object like a function and call it directly as if it were a function.
    // Inside the invoke function, a flow is created using the flow builder function. The flow builder function allows you to emit values sequentially in a flow. The flow (result of flow) will be collected by the ViewModel or any other collector that uses the GetNewsArticleUseCase, and it will receive the emitted Resource items representing the different states of the data retrieval process.
    operator fun invoke(): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())                                                                        // The flow starts by emitting a Resource.Loading() item to indicate that the data retrieval process has started.
        try {
            emit(Resource.Success(data = newsRepository.getNewsArticle()))                              // The code then attempts to fetch the news articles by calling newsRepository.getNewsArticle(). It is assumed that the NewsRepository has a method called getNewsArticle() that returns a list of articles. If the retrieval is successful and no exception occurs, the code emits a Resource.Success item into the flow, passing the fetched news articles as the data property.
        } catch (e: java.lang.Exception) {                                                              // If an exception occurs during the retrieval process, the code catches the exception and emits a Resource.Error item into the flow, passing the error message obtained from the exception as the message property.
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}