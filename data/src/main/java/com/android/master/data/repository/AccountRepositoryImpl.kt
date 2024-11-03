package com.android.master.data.repository

import com.android.master.data.datasource.auth.PreferenceDataSource
import com.android.master.domain.model.AccountInfo
import com.android.master.domain.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
) : AccountRepository {

    private val accountInfoFlow = MutableStateFlow(preferenceDataSource.getAccountInfo())

    override fun getAccountInfo(): StateFlow<AccountInfo?> {
        return accountInfoFlow
    }

    override suspend fun signIn(accountInfo: AccountInfo) {
        preferenceDataSource.putAccountInfo(accountInfo)
        accountInfoFlow.emit(accountInfo)
    }

    override suspend fun signOut() {
        preferenceDataSource.removeAccountInfo()
        accountInfoFlow.emit(null)
    }
}