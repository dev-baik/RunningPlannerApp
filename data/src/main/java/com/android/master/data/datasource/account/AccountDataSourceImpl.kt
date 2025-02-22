package com.android.master.data.datasource.account

import com.android.master.domain.model.AccountInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountDataSourceImpl @Inject constructor(
    private val accountManager: AccountManager
) : AccountDataSource {

    override fun getAccountInfo(): Flow<AccountInfo> = accountManager.accountInfo

    override suspend fun saveAccountInfo(accountInfo: AccountInfo) {
        accountManager.saveAccountInfo(accountInfo)
    }

    override suspend fun deleteAccountInfo() {
        accountManager.deleteAccountInfo()
    }
}