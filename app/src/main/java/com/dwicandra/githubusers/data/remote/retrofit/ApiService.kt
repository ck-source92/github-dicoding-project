package com.dwicandra.githubusers.data.remote.retrofit

import androidx.viewbinding.BuildConfig
import com.dwicandra.githubusers.data.remote.response.ItemsItem
import com.dwicandra.githubusers.data.remote.response.ResponseDetailUser
import com.dwicandra.githubusers.data.remote.response.ResponseUser
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<ResponseUser>

    @GET("users/{username}")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<ResponseDetailUser>

    @GET("users/{username}/followers")
    fun getFollowersUsers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowingUsers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>
}