package com.example.socialmedia.mydata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "follow")
data class Follow(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val followeeId: Int,
    val timestamp: Long = System.currentTimeMillis()
)

