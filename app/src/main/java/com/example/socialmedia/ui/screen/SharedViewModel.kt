package com.example.socialmedia.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _username = MutableLiveData<String?>()
    val username: LiveData<String?> = _username

    fun setUsername(username: String) {
        _username.value = username
    }
}
