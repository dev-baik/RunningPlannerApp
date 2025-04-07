package com.android.master.data.datasource

import com.android.master.domain.model.Profile

interface UserInfoLocalDataSource {
    var profile: Profile
    fun clear()
}