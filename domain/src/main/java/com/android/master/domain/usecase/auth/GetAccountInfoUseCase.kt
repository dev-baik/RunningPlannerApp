package com.android.master.domain.usecase.auth

import com.android.master.domain.model.AccountInfo
import com.android.master.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountInfoUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(): Flow<AccountInfo> {
        return accountRepository.getAccountInfo()
    }
}