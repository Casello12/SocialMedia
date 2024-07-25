package com.example.socialmedia.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.mydata.User
import com.example.socialmedia.mydata.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {


    fun getUser(username: String, password: String): LiveData<User?> {
        return repository.getUserByUsername(username)
    }

    fun getUserByUsername(username: String): LiveData<User?> {
        return repository.getUserByUsername(username)
    }

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

    fun getUserIdByUsername(username: String, callback: (Int?) -> Unit) {
        viewModelScope.launch {
            val userId = repository.getUserIdByUsername(username)
            callback(userId)
        }
    }

}
