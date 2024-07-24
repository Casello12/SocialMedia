package com.example.socialmedia.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.socialmedia.mydata.Like
import com.example.socialmedia.mydata.LikeRepository
import com.example.socialmedia.mydata.LikeWithContent

class LikeViewModel(private val likeRepository: LikeRepository): ViewModel() {

    val allLikes: LiveData<List<Like>> = likeRepository.getAllLikes()

    val allLikesWithContent: LiveData<List<LikeWithContent>> = likeRepository.getLikesWithPostContent()
}

class LikeViewModelFactory(private val likeRepository: LikeRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LikeViewModel::class.java)) {
            return LikeViewModel(likeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}