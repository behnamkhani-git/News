package com.example.news

import com.example.common_utils.Navigator
import com.example.news.navigation.DefaultNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MainModule {
    @Provides
    @Singleton
    fun providerProvider(): Navigator.Provider{
        return DefaultNavigator()
    }
}