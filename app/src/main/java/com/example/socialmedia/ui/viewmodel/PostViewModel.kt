package com.example.socialmedia.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.mydatapost.AppDatabase
import com.example.socialmedia.mydatapost.PostEntity
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val postDao = AppDatabase.getDatabase(application).postDao()

    fun insertPost(username: String, content: String) {
        viewModelScope.launch {
            postDao.insert(PostEntity(username = username, content = content))
        }
    }
}