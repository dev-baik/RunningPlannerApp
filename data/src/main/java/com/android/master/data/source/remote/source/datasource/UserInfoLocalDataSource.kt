package com.android.master.data.source.remote.source.datasource

import com.android.master.domain.model.Profile

interface UserInfoLocalDataSource {
    var profile: Profile
    fun clear()
}