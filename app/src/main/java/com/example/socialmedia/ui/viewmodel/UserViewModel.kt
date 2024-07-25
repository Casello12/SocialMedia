package com.example.socialmedia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.mydata.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun updatePassword(username: String, password: String) {
        viewModelScope.launch {
            repository.updatePassword(username, password)
        }
    }

    fun updateImageUri(username: String, imageUri: String) {
        viewModelScope.launch {
            repository.updateImageUri(username, imageUri)
        }
    }
}
