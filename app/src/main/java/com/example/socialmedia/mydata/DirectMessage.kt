package com.example.socialmedia.mydata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "direct_message")
data class DirectMessage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usernameWith: String,
    val usernameFrom: String,
    val contentMessage: String,
    val timestamp: Long = System.currentTimeMillis()
)