package com.android.master.domain.usecase.signin

import com.android.master.domain.model.Profile
import com.android.master.domain.repository.UserInfoRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    suspend operator fun invoke(): Profile = userInfoRepository.getProfile()
}