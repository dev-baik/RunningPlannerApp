package com.android.master.domain.repository

import com.android.master.domain.model.AccountInfo
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun getAccountInfo(): Flow<AccountInfo>

    suspend fun saveAccountInfo(accountInfo: AccountInfo)

    suspend fun deleteAccountInfo()
}