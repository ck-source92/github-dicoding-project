package com.dwicandra.githubusers.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_user")
@Parcelize
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "avatar_url")
    val avatar: String,

) : Parcelable