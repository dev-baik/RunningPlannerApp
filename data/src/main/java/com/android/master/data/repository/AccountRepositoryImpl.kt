package com.android.master.data.repository

import com.android.master.data.datasource.account.AccountDataSource
import com.android.master.domain.model.AccountInfo
import com.android.master.domain.repository.AccountRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val dataSource: AccountDataSource
) : AccountRepository {

    override val accountInfo: StateFlow<AccountInfo?> = dataSource.accountInfo

    override suspend fun saveAccountInfo(accountInfo: AccountInfo) {
        dataSource.saveAccountInfo(accountInfo)
    }

    override suspend fun deleteAccountInfo() {
        dataSource.deleteAccountInfo()
    }
}