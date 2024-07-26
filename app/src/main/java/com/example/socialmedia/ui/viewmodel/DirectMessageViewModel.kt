package com.example.socialmedia.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.mydata.DirectMessage
import com.example.socialmedia.mydata.DirectMessageRepository
import kotlinx.coroutines.launch

class DirectMessageViewModel(private val repository: DirectMessageRepository) : ViewModel() {

    fun insertMessage(directMessage: DirectMessage) {
        viewModelScope.launch {
            repository.insertMessage(directMessage)
        }
    }

    fun getMessagesBetweenUsers(usernameWith: String, usernameFrom: String): LiveData<List<DirectMessage>> {
        return repository.getMessagesBetweenUsers(usernameWith, usernameFrom)
    }

    fun getAllMessages(): LiveData<List<DirectMessage>> {
        return repository.getAllMessages()
    }
}

class DirectMessageViewModelFactory(private val repository: DirectMessageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DirectMessageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DirectMessageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}