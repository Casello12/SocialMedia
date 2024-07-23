package com.example.socialmedia.mydata

import androidx.lifecycle.LiveData

class PostRepository(private val postDao: PostDao) {

    val allPosts: LiveData<List<Post>> = postDao.getAllPosts()

    suspend fun insertPost(post: Post) {
        postDao.insertPost(post)
    }
}