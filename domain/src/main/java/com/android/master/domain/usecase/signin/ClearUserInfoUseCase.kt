package com.android.master.domain.usecase.signin

import com.android.master.domain.repository.UserInfoRepository
import javax.inject.Inject

class ClearUserInfoUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    suspend operator fun invoke() = userInfoRepository.clear()
}