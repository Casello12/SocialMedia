package com.example.socialmedia.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.mydata.Post
import com.example.socialmedia.mydata.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    // LiveData<List<Post>> for all posts
    val allPosts: LiveData<List<Post>> = repository.allPosts

    fun insertPost(username: String, content: String) {
        val post = Post(username = username, content = content)
        viewModelScope.launch {
            repository.insertPost(post)
        }
    }

    fun toggleLike(postId: Int, username: String, liked: Boolean) {
        viewModelScope.launch {
            if (liked) {
                repository.insertLike(postId, username)
            } else {
                repository.removeLike(postId, username)
            }
        }
    }

    suspend fun isPostLiked(postId: Int, username: String): Boolean {
        return repository.getLike(postId, username) != null
    }
}