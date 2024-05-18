package com.example.arquitecturamvvm.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val uuid: String,
    val email: String,
    val name: String
)