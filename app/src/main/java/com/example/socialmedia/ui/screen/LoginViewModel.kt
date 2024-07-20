package com.example.socialmedia.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.mydata.UserRepository
import com.example.socialmedia.mydata.User
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun insertUser(username: String, password: String) {
        viewModelScope.launch {
            repository.insertUser(User(username = username, password = password))
        }
    }

    fun getUser(username: String, password: String, callback: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUser(username, password)
            callback(user)
        }
    }
}
