package com.silence.experimental.common.data

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import javax.inject.Inject


class NetworkHandler @Inject constructor(private val cm: ConnectivityManager) {

    @RequiresPermission(ACCESS_NETWORK_STATE)
    fun hasNetworkConnection(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nc = cm.getNetworkCapabilities(cm.activeNetwork)
            hasNetworkConnection(nc)
        } else {
            val networkInfo = cm.activeNetworkInfo
            hasNetworkConnection(networkInfo)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun hasNetworkConnection(@Nullable nc: NetworkCapabilities?): Boolean {
        return nc != null && (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || nc.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        ))
    }

    private fun hasNetworkConnection(@Nullable networkInfo: NetworkInfo?): Boolean {
        return networkInfo != null && networkInfo.isConnected
    }
}