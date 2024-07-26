package com.example.socialmedia.mydata

import androidx.lifecycle.LiveData

class FollowRepository(private val followDao: FollowDao) {

    suspend fun insertFollow(follow: Follow) {
        followDao.insertFollow(follow)
    }

    suspend fun deleteFollow(follow: Follow) {
        followDao.deleteFollow(follow)
    }

    suspend fun getFollow(userId: Int, followeeId: Int): Follow? {
        return followDao.getFollow(userId, followeeId)
    }

    suspend fun getFollowings(userId: Int): List<Follow> {
        return followDao.getFollowings(userId)
    }

    suspend fun getFollowers(followeeId: Int): List<Follow> {
        return followDao.getFollowers(followeeId)
    }

    suspend fun isFollowing(userId: Int, followeeId: Int): Boolean {
        return followDao.isFollowing(userId, followeeId)
    }

    fun getFollowingsByUserId(userId: Int): LiveData<List<Follow>> {
        return followDao.getFollowingsByUserId(userId)
    }
}