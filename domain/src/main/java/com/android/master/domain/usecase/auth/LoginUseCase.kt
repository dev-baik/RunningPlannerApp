package com.android.master.domain.usecase.auth

import com.android.master.domain.model.AccountInfo
import com.android.master.domain.repository.AccountRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(accountInfo: AccountInfo) {
        return accountRepository.saveAccountInfo(accountInfo)
    }
}