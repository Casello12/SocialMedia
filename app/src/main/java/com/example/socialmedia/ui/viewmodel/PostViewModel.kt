package com.example.socialmedia.ui.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.mydata.AppDatabase
import com.example.socialmedia.mydata.Post
import com.example.socialmedia.mydata.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    val allPosts = repository.allPosts

    fun insertPost(username: String, content: String) {
        val post = Post(username = username, content = content)
        viewModelScope.launch {
            repository.insertPost(post)
        }
    }
}