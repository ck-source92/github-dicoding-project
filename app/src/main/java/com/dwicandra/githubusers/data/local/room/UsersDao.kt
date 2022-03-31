package com.dwicandra.githubusers.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dwicandra.githubusers.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Query("SELECT * FROM favorite_user ")
    fun getAllFavoriteUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(user: UserEntity)

    @Delete
    suspend fun deleteFomFavorite(user: UserEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE username = :username)")
    fun checkUser(username: String): Flow<Boolean>

}