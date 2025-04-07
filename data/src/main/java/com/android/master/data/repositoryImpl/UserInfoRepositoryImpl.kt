package com.android.master.data.repositoryImpl

import com.android.master.data.datasource.UserInfoLocalDataSource
import com.android.master.domain.model.Profile
import com.android.master.domain.repository.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val userInfoLocalDataSource: UserInfoLocalDataSource
) : UserInfoRepository {

    override suspend fun getProfile(): Profile = userInfoLocalDataSource.profile

    override suspend fun setProfile(profile: Profile) {
        userInfoLocalDataSource.profile = profile
    }

    override suspend fun clear() {
        userInfoLocalDataSource.clear()
    }
}