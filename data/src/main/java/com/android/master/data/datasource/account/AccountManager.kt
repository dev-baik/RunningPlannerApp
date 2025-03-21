package com.android.master.data.datasource.account

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.android.master.domain.model.AccountInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AccountManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val accountInfo: Flow<AccountInfo> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            AccountInfo(
                preferences[ACCOUNT_PREF_ID].orEmpty(),
                preferences[ACCOUNT_PREF_EMAIL].orEmpty(),
                preferences[ACCOUNT_PREF_TYPE]?.let { AccountInfo.LoginType.valueOf(it) }
            )
        }

    suspend fun saveAccountInfo(accountInfo: AccountInfo) {
        dataStore.edit {
            it[ACCOUNT_PREF_ID] = accountInfo.id
            it[ACCOUNT_PREF_EMAIL] = accountInfo.email
            it[ACCOUNT_PREF_TYPE] = accountInfo.type?.name.orEmpty()
        }
    }

    suspend fun deleteAccountInfo() {
        dataStore.edit {
            it.remove(ACCOUNT_PREF_ID)
            it.remove(ACCOUNT_PREF_EMAIL)
            it.remove(ACCOUNT_PREF_TYPE)
        }
    }

    companion object {
        private const val ACCOUNT_ID = "id"
        private const val ACCOUNT_EMAIL = "email"
        private const val ACCOUNT_TYPE = "type"
        val ACCOUNT_PREF_ID = stringPreferencesKey(ACCOUNT_ID)
        val ACCOUNT_PREF_EMAIL = stringPreferencesKey(ACCOUNT_EMAIL)
        val ACCOUNT_PREF_TYPE = stringPreferencesKey(ACCOUNT_TYPE)
    }
}