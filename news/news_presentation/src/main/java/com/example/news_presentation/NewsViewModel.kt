package com.example.news_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common_utils.Resource
import com.example.news_domain.use_case.GetNewsArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
The ViewModel has a constructor that receives an instance of the GetNewsArticleUseCase.
This use case is responsible for fetching news articles from a data source.

Overall, this ViewModel is responsible for coordinating the retrieval of news articles and
updating the state flow (newsArticle) accordingly, allowing UI components to observe and react to
the changes in the news article data.
 */
@HiltViewModel                                                                                          // The ViewModel uses the @HiltViewModel annotation, which is part of the Hilt library for dependency injection in Android. This annotation allows the ViewModel to be injected using Hilt.
class NewsViewModel @Inject constructor(private val getNewsArticleUseCase: GetNewsArticleUseCase) :
    ViewModel() {

    private val _newsArticle = MutableStateFlow(NewsState())                                            // The ViewModel defines a private mutable state flow called _newsArticle of type NewsState. A state flow is a type of flow that represents a stream of values, and it emits the latest value to its collectors. The leading underscore in _newsArticle indicates that it's a private property.
    val newsArticle: StateFlow<NewsState> = _newsArticle                                                // The ViewModel exposes a public immutable state flow called newsArticle of type StateFlow<NewsState>. By exposing it as a state flow, external components can observe the state changes emitted by the ViewModel.


    init {                                                                                              // Inside the init block, the ViewModel calls the getNewsArticle() function to initiate the process of fetching news articles. The init block is a special block that is executed as soon as an instance of the ViewModel is created. Placing getNewsArticle() inside the init block ensures that the process of fetching news articles starts as soon as the ViewModel is created.
        getNewsArticle()
    }

    private fun getNewsArticle() {                                                                      // The getNewsArticle() function invokes the getNewsArticleUseCase to fetch the news articles.
        getNewsArticleUseCase().onEach { newsResponse ->                                                // newsResponse is of type Resource<List<Article>>. The onEach operator is used on the flow returned by getNewsArticleUseCase(). This operator allows you to perform actions for each emission of the flow.
            when (newsResponse) {                                                                       // The getNewsArticleUseCase returns a flow of Resource objects that represent different states of the data retrieval process (e.g., loading, success, error). Inside the onEach block, the function checks the type of the Resource emission and updates the _newsArticle state flow accordingly.
                is Resource.Loading -> {
                    _newsArticle.value = NewsState(isLoading = true)
                }
                is Resource.Error -> {
                    _newsArticle.value = NewsState(error = newsResponse.message)
                }
                is Resource.Success -> {
                    _newsArticle.value = NewsState(data = newsResponse.data)
                }
            }
        }.launchIn(viewModelScope)                                                                      // The launchIn operator is used to start collecting values from a flow within the viewModelScope. The viewModelScope is a predefined CoroutineScope tied to the lifecycle of the ViewModel. This ensures that the flow collection is canceled when the ViewModel is destroyed, preventing any potential memory leaks. So by using launchIn(viewModelScope), the flow collection is automatically canceled when the ViewModel is destroyed, preventing any potential memory leaks or unnecessary computations.
    }
}