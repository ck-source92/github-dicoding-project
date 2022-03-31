package com.dwicandra.githubusers.di

import android.content.Context
import com.dwicandra.githubusers.data.local.room.GithubDatabase
import com.dwicandra.githubusers.data.remote.retrofit.ApiConfig
import com.dwicandra.githubusers.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = GithubDatabase.getInstance(context)
        val dao = database.favoriteUsersDao()
        return UserRepository.getInstance(apiService, dao)
    }
}