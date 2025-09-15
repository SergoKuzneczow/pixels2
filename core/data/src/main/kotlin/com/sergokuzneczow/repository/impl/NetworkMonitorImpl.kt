package com.sergokuzneczow.repository.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.sergokuzneczow.models.NetworkState
import com.sergokuzneczow.repository.api.NetworkMonitorApi
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
public class NetworkMonitorImpl @Inject constructor(
    private val context: Context,
) : NetworkMonitorApi {

    override fun networkStateFlow(): StateFlow<Boolean> = defaultNetworkStateFlow

    private val defaultNetworkStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        createConnectivityManagerCallback()
    }

    private fun createConnectivityManagerCallback() {

        val callback = object : ConnectivityManager.NetworkCallback() {

//            init {
//                defaultNetworkStateFlow.tryEmit(
//                    NetworkState(
//                        id = 0L,
//                        isActive = false,
//                        isNotMetered = false,
//                    )
//                )
//            }

            private var lastCapabilityNetworkState: NetworkState? = null

            private var lastCapabilityStatus: Boolean? = null

            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                val id = network.networkHandle
                val isNotMetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
                val isCapability = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

                if (lastCapabilityStatus != isCapability) {
                    defaultNetworkStateFlow.tryEmit(isCapability)
                }

                lastCapabilityStatus = isCapability

//                val newNetworkState = NetworkState(
//                    id = id,
//                    isActive = isCapability,
//                    isNotMetered = isNotMetered
//                )

//                if (lastCapabilityNetworkState != newNetworkState) {
//                    defaultNetworkStateFlow.tryEmit(newNetworkState)
//                }

//                lastCapabilityNetworkState = newNetworkState
            }

            override fun onLost(network: Network) {
//                defaultNetworkStateFlow.tryEmit(
//                    NetworkState(
//                        id = network.networkHandle,
//                        isActive = false,
//                        isNotMetered = false,
//                    )
//                )
                defaultNetworkStateFlow.tryEmit(false)
            }
        }
        context.getSystemService(ConnectivityManager::class.java).registerDefaultNetworkCallback(callback)
    }
}