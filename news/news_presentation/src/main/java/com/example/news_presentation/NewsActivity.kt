package com.example.news_presentation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.common_utils.Navigator
import com.example.news_presentation.databinding.ActivityNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(activity: Activity) {
            val intent = Intent(activity, NewsActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var _binding: ActivityNewsBinding? = null
    private val binding: ActivityNewsBinding
        get() = _binding!!

    private val newsViewModel: NewsViewModel by viewModels()

    private val newsAdapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setObservers()
    }

    private fun initView() {
        binding.rvArticles.adapter = newsAdapter
    }

    /*
    This function listens to changes in the newsViewModel.newsArticle data. When the data changes, the observer
    reacts accordingly by updating the user interface (UI) of the NewsActivity based on the state of the data.
     */
    private fun setObservers() {
        lifecycleScope.launchWhenCreated {
            newsViewModel.newsArticle.collectLatest { newsState ->             // Inside the lifecycleScope.launchWhenCreated block, an observer is set up for the newsViewModel.newsArticle data using the collectLatest function. This allows the code to be notified whenever the data changes.
                                                                               // Also we have used .collectLatest instead of .collect, because we only need the last emission. If we use .collect, it collects all the emissions.
                if (newsState.isLoading) {                                     // When the data is being loaded (it.isLoading is true), the progress bar (binding.progressBar) is set to visible, indicating to the user that the data is being loaded. This can be beneficial for efficiency and avoiding redundant processing.
                    binding.progressBar.visibility = View.VISIBLE
                } else if (newsState.error.isNotBlank()) {                     // When the data is being loaded (it.isLoading is true), the progress bar (binding.progressBar) is set to visible, indicating to the user that the data is being loaded.
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@NewsActivity, newsState.error, Toast.LENGTH_LONG).show()
                }
                newsState.data?.let {
                    binding.progressBar.visibility = View.GONE
                    newsAdapter.setData(it)                                     // The newsAdapter.setData method is called to update the adapter with the new data, which will eventually update the RecyclerView (binding.rvArticles) in the UI with the new articles.
                }
            }
        }
    }
}

object GoToNewsActivity : Navigator {
    override fun navigate(activity: Activity) {
        NewsActivity.launchActivity(activity)
    }
}