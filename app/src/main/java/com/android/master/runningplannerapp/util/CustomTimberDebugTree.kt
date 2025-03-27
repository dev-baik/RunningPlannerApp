package com.android.master.runningplannerapp.util

import timber.log.Timber
import javax.inject.Inject

class CustomTimberDebugTree @Inject constructor() : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String? {
        return "${element.className}:${element.lineNumber}#${element.methodName}"
    }
}