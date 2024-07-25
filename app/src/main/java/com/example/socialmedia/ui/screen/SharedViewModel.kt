package com.example.socialmedia.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _username = MutableLiveData<String?>()
    val username: LiveData<String?> get() = _username

    private val _imageUri = MutableLiveData<String?>()
    val imageUri: LiveData<String?> get() = _imageUri

    fun setUsername(username: String?) {
        _username.value = username
    }

    fun setImageUri(uri: String?) {
        _imageUri.value = uri
    }
}
