package com.example.socialmedia.mydata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUser(username: String, password: String): User?

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    fun getUserByUsername(username: String): LiveData<User?>

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    suspend fun getUserByUsernameSync(username: String): User?

    @Query("UPDATE user SET password = :password WHERE username = :username")
    suspend fun updatePassword(username: String, password: String)

    @Query("UPDATE user SET imageUri = :imageUri WHERE username = :username")
    suspend fun updateImageUri(username: String, imageUri: String)

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUserIdByUsername(username: String): LiveData<User?>
}