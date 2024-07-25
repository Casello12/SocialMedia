package com.example.socialmedia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.mydata.Follow
import com.example.socialmedia.mydata.FollowRepository
import kotlinx.coroutines.launch

class FollowViewModel(private val repository: FollowRepository) : ViewModel() {

    fun insertFollow(userId: Int, followeeId: Int) {
        viewModelScope.launch {
            repository.insertFollow(Follow(userId = userId, followeeId = followeeId))
        }
    }

    fun deleteFollow(userId: Int, followeeId: Int) {
        viewModelScope.launch {
            val follow = repository.getFollow(userId, followeeId)
            if (follow != null) {
                repository.deleteFollow(follow)
            }
        }
    }

    fun isFollowing(userId: Int, followeeId: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFollowing = repository.isFollowing(userId, followeeId)
            callback(isFollowing)
        }
    }
}

class FollowViewModelFactory(private val repository: FollowRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FollowViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}