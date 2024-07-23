package com.example.socialmedia.mydatapost

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {
    @Insert
    suspend fun insert(post: PostEntity)

    @Query("SELECT * FROM posts")
    fun getAllPosts(): LiveData<List<PostEntity>>
}