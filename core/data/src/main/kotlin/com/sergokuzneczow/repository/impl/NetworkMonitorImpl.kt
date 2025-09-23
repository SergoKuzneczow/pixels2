package com.sergokuzneczow.repository.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.sergokuzneczow.repository.api.NetworkMonitorApi
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
public class NetworkMonitorImpl @Inject constructor(
    context: Context,
) : NetworkMonitorApi {

    override fun networkStateFlow(): StateFlow<Boolean> = defaultNetworkStateFlow

    private val defaultNetworkStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val callback = object : ConnectivityManager.NetworkCallback() {

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            val isCapability = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            defaultNetworkStateFlow.tryEmit(isCapability)
        }

        override fun onLost(network: Network) {
            defaultNetworkStateFlow.tryEmit(false)
        }
    }

    init {
        context.getSystemService(ConnectivityManager::class.java).registerDefaultNetworkCallback(callback)
    }
}