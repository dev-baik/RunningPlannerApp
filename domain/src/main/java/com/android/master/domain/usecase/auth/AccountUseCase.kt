package com.android.master.domain.usecase.auth

import com.android.master.domain.model.AccountInfo
import com.android.master.domain.repository.AccountRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    fun getAccountInfo(): StateFlow<AccountInfo?> {
        return accountRepository.getAccountInfo()
    }

    suspend fun signIn(accountInfo: AccountInfo) {
        accountRepository.signIn(accountInfo)
    }

    suspend fun signOut() {
        accountRepository.signOut()
    }
}