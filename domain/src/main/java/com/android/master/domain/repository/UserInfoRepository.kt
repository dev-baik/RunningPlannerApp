package com.android.master.domain.repository

import com.android.master.domain.model.Profile

interface UserInfoRepository {

    suspend fun getProfile(): Profile

    suspend fun setProfile(profile: Profile)

    suspend fun clear()
}