package com.example.socialmedia.mydata

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUser(username: String, password: String): User? {
        return userDao.getUser(username, password)
    }

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    suspend fun updatePassword(username: String, password: String) {
        userDao.updatePassword(username, password)
    }

    suspend fun updateImageUri(username: String, imageUri: String) {
        userDao.updateImageUri(username, imageUri)
    }
}