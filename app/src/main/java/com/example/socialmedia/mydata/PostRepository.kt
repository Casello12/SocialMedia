package com.example.socialmedia.mydata

import androidx.lifecycle.LiveData

class PostRepository(
    private val postDao: PostDao,
    private val likeDao: LikeDao
) {

    val allPosts: LiveData<List<Post>> = postDao.getAllPosts()

    suspend fun insertPost(post: Post) {
        postDao.insertPost(post)
    }

    suspend fun insertLike(postId: Int, username: String) {
        likeDao.insertLike(Like(postId = postId, username = username))
    }

    suspend fun removeLike(postId: Int, username: String) {
        likeDao.removeLike(postId, username)
    }

    suspend fun getLike(postId: Int, username: String): Like? {
        return likeDao.getLikeByPostIdAndUsername(postId, username)
    }
}