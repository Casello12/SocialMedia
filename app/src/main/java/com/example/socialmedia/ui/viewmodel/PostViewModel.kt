package com.example.socialmedia.ui.viewmodel

import android.app.Application
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
}