package com.android.master.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.connectivityManager(): ConnectivityManager {
    return getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}

fun Context.isWifiConnected(): Boolean {
    val networkCapabilities = connectivityManager().getNetworkCapabilities(connectivityManager().activeNetwork)
    return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
}

fun Context.isMobileDataConnected(): Boolean {
    val networkCapabilities = connectivityManager().getNetworkCapabilities(connectivityManager().activeNetwork)
    return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
}

fun Context.isNetworkAvailable(): Boolean {
    return !isWifiConnected() && !isMobileDataConnected()
}