package com.android.master.runningplannerapp

import android.app.Application
import com.android.master.runningplannerapp.util.CustomTimberDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class RPApplication : Application() {

    @Inject
    lateinit var customTimberTree: CustomTimberDebugTree

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(customTimberTree)
        }
    }
}
