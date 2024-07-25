package com.example.socialmedia.mydata

class FollowRepository(private val followDao: FollowDao) {

    suspend fun insertFollow(follow: Follow) {
        followDao.insertFollow(follow)
    }

    suspend fun deleteFollow(follow: Follow) {
        followDao.deleteFollow(follow)
    }

    suspend fun getFollow(followerId: Int, followeeId: Int): Follow? {
        return followDao.getFollow(followerId, followeeId)
    }

    suspend fun getFollowings(followerId: Int): List<Follow> {
        return followDao.getFollowings(followerId)
    }

    suspend fun getFollowers(followeeId: Int): List<Follow> {
        return followDao.getFollowers(followeeId)
    }

    suspend fun isFollowing(followerId: Int, followeeId: Int): Boolean {
        return followDao.getFollow(followerId, followeeId) != null
    }
}