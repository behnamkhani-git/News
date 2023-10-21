package com.example.news

import android.content.Context
import com.example.common_utils.Navigator
import com.example.news.navigation.DefaultNavigator
import com.example.news.room.AppDatabase
import com.example.news_data.room.NewsDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MainModule {

    @Provides
    @Singleton
    fun providerProvider(): Navigator.Provider {
        return DefaultNavigator()
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideNewsDAO(appDatabase: AppDatabase): NewsDAO {
        return appDatabase.getNewsDao()
    }
}