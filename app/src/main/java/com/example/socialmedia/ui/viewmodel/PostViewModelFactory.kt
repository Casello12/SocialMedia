package com.example.socialmedia.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.socialmedia.mydata.AppDatabase
import com.example.socialmedia.mydata.PostRepository

class PostViewModelFactory(private val repository: PostRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}