package com.rodriguez.nodocivico.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {

            Toast.makeText(
                context,
                "Internet conectado",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            Toast.makeText(
                context,
                "Sin conexión a internet",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}