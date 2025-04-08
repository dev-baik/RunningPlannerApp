package com.android.master.data.datasourceImpl

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.android.master.data.BuildConfig
import com.android.master.data.datasource.UserInfoLocalDataSource
import com.android.master.domain.model.Profile
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserInfoLocalDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : UserInfoLocalDataSource {

    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = if (BuildConfig.DEBUG) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    } else {
        EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private val gson = Gson()

    override var profile: Profile
        get() = gson.fromJson(sharedPreferences.getString(PROFILE, INITIAL_VALUE), Profile::class.java) ?: Profile()
        set(value) { sharedPreferences.edit { putString(PROFILE, gson.toJson(value)) } }

    override fun clear() = sharedPreferences.edit { clear() }

    companion object {
        const val FILE_NAME = "RPAppLocalDataSource"
        const val PROFILE = "Profile"
        const val INITIAL_VALUE = ""
    }
}