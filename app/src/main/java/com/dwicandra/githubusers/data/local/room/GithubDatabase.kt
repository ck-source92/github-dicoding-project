package com.dwicandra.githubusers.data.local.room

import android.content.Context
import androidx.room.*
import com.dwicandra.githubusers.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun favoriteUsersDao(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null
        fun getInstance(context: Context): GithubDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java,
                    "User.db"
                ).build()
            }
    }
}