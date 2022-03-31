package com.dwicandra.githubusers.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.dwicandra.githubusers.data.local.entity.UserEntity
import com.dwicandra.githubusers.data.local.result.Result
import com.dwicandra.githubusers.data.local.room.UsersDao
import com.dwicandra.githubusers.data.remote.response.ItemsItem
import com.dwicandra.githubusers.data.remote.response.ResponseDetailUser
import com.dwicandra.githubusers.data.remote.response.ResponseUser
import com.dwicandra.githubusers.data.remote.retrofit.ApiConfig
import com.dwicandra.githubusers.data.remote.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val usersDao: UsersDao
) {
    private val _listGithubUsers = MutableLiveData<ArrayList<ItemsItem>>()
    val getListGithubUsersLiveData: LiveData<ArrayList<ItemsItem>> = _listGithubUsers

    private val _detailUserGithub = MutableLiveData<ResponseDetailUser>()
    val getDetailGithubUsers: LiveData<ResponseDetailUser> = _detailUserGithub

    private val _listFollowersGithub = MutableLiveData<ArrayList<ItemsItem>>()
    val getListFollowersUser: LiveData<ArrayList<ItemsItem>> = _listFollowersGithub

    private val _listFollowingGithub = MutableLiveData<ArrayList<ItemsItem>>()
    val getListFollowingUser: LiveData<ArrayList<ItemsItem>> = _listFollowingGithub

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val result = MediatorLiveData<Result<ArrayList<ItemsItem>>>()

    fun getSearchUsers(query: String) {
        _isLoading.value = true
        val client = apiService.getSearchUsers(query)
        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listGithubUsers.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                _isLoading.value = true
                result.value = Result.Error(t.message.toString())
            }
        })
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUsers(username)
        client.enqueue(object : Callback<ResponseDetailUser> {
            override fun onResponse(
                call: Call<ResponseDetailUser>,
                response: Response<ResponseDetailUser>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUserGithub.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ResponseDetailUser>, t: Throwable) {
                _isLoading.value = false
                result.value = Result.Error(t.message.toString())
            }
        })
    }

    fun getFollowersUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowersUsers(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowersGithub.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                result.value = Result.Error(t.message.toString())
            }
        })
    }

    fun getFollowingUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowingUsers(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowingGithub.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                result.value = Result.Error(t.message.toString())
            }
        })
    }

    fun getAllFavoriteUsers(): LiveData<List<UserEntity>> {
        return usersDao.getAllFavoriteUsers()
    }

    suspend fun setFavoriteUser(user: UserEntity) {
        _isFavorite.value = true
        usersDao.insertFavorite(user)
    }

    suspend fun deleteUser(user: UserEntity) {
        _isFavorite.value = false
        usersDao.deleteFomFavorite(user)
    }

    fun checkUser(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            usersDao.checkUser(username).collect {
                _isFavorite.postValue(it)
            }
        }
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            usersDao: UsersDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, usersDao)
            }.also { instance = it }
    }
}