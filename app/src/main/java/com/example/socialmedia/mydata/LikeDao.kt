package com.example.socialmedia.mydata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikeDao {
    @Insert
    suspend fun insertLike(like: Like)

    @Query("DELETE FROM likes WHERE postId = :postId AND username = :username")
    suspend fun removeLike(postId: Int, username: String)

    @Query("SELECT * FROM likes WHERE postId = :postId AND username = :username")
    suspend fun getLikeByPostIdAndUsername(postId: Int, username: String): Like?

    @Query("SELECT * FROM likes WHERE postId = :postId")
    suspend fun getLikeByPostId(postId: Int): List<Like>
}