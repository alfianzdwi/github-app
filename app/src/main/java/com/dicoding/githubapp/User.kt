package com.dicoding.githubapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val avatar: Int,
    val username: String,
    val name: String,
    val location: String,
    val repository: String,
    val company: String,
    val followers: String,
    val following: String
): Parcelable