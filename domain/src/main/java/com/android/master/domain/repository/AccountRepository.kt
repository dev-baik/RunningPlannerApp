package com.android.master.domain.repository

import com.android.master.domain.model.AccountInfo
import kotlinx.coroutines.flow.StateFlow

interface AccountRepository {

    fun getAccountInfo(): StateFlow<AccountInfo?>

    suspend fun signIn(accountInfo: AccountInfo)

    suspend fun signOut()
}