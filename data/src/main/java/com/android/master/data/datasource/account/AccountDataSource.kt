package com.android.master.data.datasource.account

import com.android.master.domain.model.AccountInfo
import kotlinx.coroutines.flow.Flow

interface AccountDataSource {

    fun getAccountInfo(): Flow<AccountInfo>

    suspend fun saveAccountInfo(accountInfo: AccountInfo)

    suspend fun deleteAccountInfo()
}