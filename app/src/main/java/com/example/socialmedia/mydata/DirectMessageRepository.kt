package com.example.socialmedia.mydata

import androidx.lifecycle.LiveData

class DirectMessageRepository(private val directMessageDao: DirectMessageDao) {

    suspend fun insertMessage(directMessage: DirectMessage) {
        directMessageDao.insertMessage(directMessage)
    }

    fun getMessagesBetweenUsers(usernameWith: String, usernameFrom: String): LiveData<List<DirectMessage>> {
        return directMessageDao.getMessagesBetweenUsers(usernameWith, usernameFrom)
    }

    fun getAllMessages(): LiveData<List<DirectMessage>> {
        return directMessageDao.getAllMessages()
    }
}