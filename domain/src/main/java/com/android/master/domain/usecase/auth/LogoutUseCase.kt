package com.android.master.domain.usecase.auth

import com.android.master.domain.repository.AccountRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() {
        return accountRepository.deleteAccountInfo()
    }
}