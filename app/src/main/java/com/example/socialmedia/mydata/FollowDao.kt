package com.example.socialmedia.mydata

import androidx.lifecycle.LiveData
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

    @Query("SELECT * FROM follow WHERE userId = :userId AND followeeId = :followeeId LIMIT 1")
    suspend fun getFollow(userId: Int, followeeId: Int): Follow?

    @Query("SELECT * FROM follow WHERE userId = :userId")
    suspend fun getFollowings(userId: Int): List<Follow>

    @Query("SELECT * FROM follow WHERE followeeId = :followeeId")
    suspend fun getFollowers(followeeId: Int): List<Follow>

    @Query("SELECT EXISTS(SELECT 1 FROM follow WHERE userId = :userId AND followeeId = :followeeId LIMIT 1)")
    suspend fun isFollowing(userId: Int, followeeId: Int): Boolean

    @Query("SELECT * FROM follow WHERE userId = :userId")
    fun getFollowingsByUserId(userId: Int): LiveData<List<Follow>>
}