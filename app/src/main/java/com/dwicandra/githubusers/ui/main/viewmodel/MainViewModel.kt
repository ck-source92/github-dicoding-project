package com.dwicandra.githubusers.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwicandra.githubusers.data.local.entity.UserEntity
import com.dwicandra.githubusers.data.remote.response.ItemsItem
import com.dwicandra.githubusers.data.remote.response.ResponseDetailUser
import com.dwicandra.githubusers.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    var listGithubUsersLiveData: LiveData<ArrayList<ItemsItem>>? =
        userRepository.getListGithubUsersLiveData

    var detailGithubUserLiveData: LiveData<ResponseDetailUser>? =
        userRepository.getDetailGithubUsers

    var listFollowersGithubUsersLiveData: LiveData<ArrayList<ItemsItem>>? =
        userRepository.getListFollowersUser

    var listFollowingGithubUsersLiveData: LiveData<ArrayList<ItemsItem>>? =
        userRepository.getListFollowingUser

    val isLoading: LiveData<Boolean> = userRepository.isLoading

    val isFavorite: LiveData<Boolean> = userRepository.isFavorite

    fun getSearchUsers(query: String) {
        userRepository.getSearchUsers(query)
    }

    fun getDetailUsers(username: String) {
        userRepository.getDetailUser(username)
    }

    fun getFollower(username: String) {
        userRepository.getFollowersUsers(username)
    }

    fun getFollowing(username: String) {
        userRepository.getFollowingUsers(username)
    }

    fun getAllFavoriteUsers() = userRepository.getAllFavoriteUsers()

    fun setFavoriteUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.setFavoriteUser(user)
        }
    }

    fun deleteFavoriteUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }

    fun checkUser(username: String) {
        userRepository.checkUser(username)
    }

}