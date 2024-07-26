package com.example.socialmedia.mydata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DirectMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(directMessage: DirectMessage)

    @Query("SELECT * FROM direct_message WHERE (usernameWith = :usernameWith AND usernameFrom = :usernameFrom) OR (usernameWith = :usernameFrom AND usernameFrom = :usernameWith) ORDER BY timestamp ASC")
    fun getMessagesBetweenUsers(usernameWith: String, usernameFrom: String): LiveData<List<DirectMessage>>

    @Query("SELECT * FROM direct_message ORDER BY timestamp DESC")
    fun getAllMessages(): LiveData<List<DirectMessage>>
}