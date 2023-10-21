package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import com.example.common_utils.Activities
import com.example.common_utils.Navigator
import com.example.news.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var provider:Navigator.Provider

    // The purpose of this code is to handle the nullable nature of the binding object.
    // Since the binding is typically set in the onCreate() method of the activity, it could be null until that point.
    private var _binding:ActivityMainBinding? = null

    // A read-only property binding of type ActivityMainBinding
    private val binding:ActivityMainBinding
    get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Wait 2 seconds to show the Splash Screen
        Handler().postDelayed({
            provider.getActivities(Activities.NewsActivity).navigate(this)
            finish()
        }, 2000)
    }
}