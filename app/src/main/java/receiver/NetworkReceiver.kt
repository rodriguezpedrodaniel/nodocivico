package com.rodriguez.nodocivico.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent?
    ) {

        if (hayInternet(context)) {

            Toast.makeText(
                context,
                "🌐 Internet conectado",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            Toast.makeText(
                context,
                "📡 Sin conexión a internet",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun hayInternet(
        context: Context
    ): Boolean {

        val connectivityManager =
            context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager

        return if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.M
        ) {

            val network =
                connectivityManager.activeNetwork
                    ?: return false

            val capabilities =
                connectivityManager.getNetworkCapabilities(
                    network
                ) ?: return false

            capabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET
            ) &&
                    capabilities.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_VALIDATED
                    )

        } else {

            @Suppress("DEPRECATION")
            val networkInfo =
                connectivityManager.activeNetworkInfo

            @Suppress("DEPRECATION")
            networkInfo != null &&
                    networkInfo.isConnected
        }
    }
}