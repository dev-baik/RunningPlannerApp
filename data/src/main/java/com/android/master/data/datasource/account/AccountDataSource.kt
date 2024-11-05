package com.android.master.data.datasource.account

import com.android.master.domain.model.AccountInfo
import kotlinx.coroutines.flow.StateFlow

interface AccountDataSource {

    val accountInfo: StateFlow<AccountInfo?>

    suspend fun saveAccountInfo(accountInfo: AccountInfo)

    suspend fun deleteAccountInfo()
}