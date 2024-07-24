package com.example.socialmedia.mydata

import androidx.lifecycle.LiveData
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

    @Query("SELECT * FROM likes")
    fun getAllLikes(): LiveData<List<Like>>

    @Query("""
        SELECT l.postId, p.content, l.username
        FROM likes l
        INNER JOIN post p ON l.postId = p.id
    """)
    fun getLikesWithPostContent(): LiveData<List<LikeWithContent>>
}