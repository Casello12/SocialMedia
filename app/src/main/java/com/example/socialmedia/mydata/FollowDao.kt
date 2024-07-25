package com.example.socialmedia.mydata

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FollowDao {

    @Insert
    suspend fun insertFollow(follow: Follow)

    @Delete
    suspend fun deleteFollow(follow: Follow)

    @Query("SELECT * FROM follow WHERE followerId = :followerId AND followeeId = :followeeId LIMIT 1")
    suspend fun getFollow(followerId: Int, followeeId: Int): Follow?

    @Query("SELECT * FROM follow WHERE followerId = :followerId")
    suspend fun getFollowings(followerId: Int): List<Follow>

    @Query("SELECT * FROM follow WHERE followeeId = :followeeId")
    suspend fun getFollowers(followeeId: Int): List<Follow>
}