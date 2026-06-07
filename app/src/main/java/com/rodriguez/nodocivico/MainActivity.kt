package com.rodriguez.nodocivico

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rodriguez.nodocivico.network.RetrofitClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitClient.appContext = applicationContext
        setContentView(R.layout.activity_main)
    }
}