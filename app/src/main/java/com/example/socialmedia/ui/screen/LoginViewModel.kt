package com.example.socialmedia.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.mydata.UserRepository
import com.example.socialmedia.mydata.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun insertUser(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.getUserByUsernameSync(username)
            if (user == null) {
                repository.insertUser(User(username = username, password = password))
            }
        }
    }

    fun getUser(username: String, password: String, callback: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUser(username, password)
            callback(user)
        }
    }

    fun getUserByUsername(username: String, callback: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByUsername(username).value
            callback(user)
        }
    }
}
