package com.rodriguez.nodocivico.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class PowerReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent?
    ) {

        when (intent?.action) {

            Intent.ACTION_POWER_CONNECTED -> {

                Toast.makeText(
                    context,
                    "⚡ Cargador conectado",
                    Toast.LENGTH_SHORT
                ).show()
            }

            Intent.ACTION_POWER_DISCONNECTED -> {

                Toast.makeText(
                    context,
                    "🔋 Cargador desconectado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}