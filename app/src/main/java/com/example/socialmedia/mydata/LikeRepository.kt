package com.example.socialmedia.mydata

import androidx.lifecycle.LiveData

class LikeRepository(private val likeDao: LikeDao) {

    // Retrieve all likes from the database
    fun getAllLikes(): LiveData<List<Like>> = likeDao.getAllLikes()

    // Insert a new like into the database
    suspend fun insertLike(like: Like) {
        likeDao.insertLike(like)
    }

    // Optionally, you can add other methods like delete or update
    fun getLikesWithPostContent(): LiveData<List<LikeWithContent>> {
        return likeDao.getLikesWithPostContent()
    }
}