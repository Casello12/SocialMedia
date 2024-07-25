package com.example.socialmedia.mydata

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUser(username: String, password: String): User? {
        return userDao.getUser(username, password)
    }

    fun getUserByUsername(username: String): LiveData<User?> {
        return userDao.getUserByUsername(username)
    }

    suspend fun getUserByUsernameSync(username: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUserByUsernameSync(username)
        }
    }

    suspend fun updatePassword(username: String, password: String) {
        userDao.updatePassword(username, password)
    }

    suspend fun updateImageUri(username: String, imageUri: String) {
        userDao.updateImageUri(username, imageUri)
    }

    suspend fun getUserIdByUsername(username: String): Int? {
        return userDao.getUserIdByUsername(username)
    }
}