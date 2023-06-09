package com.tevah.pfe_v4final

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val value = sharedPref.getString("Token", "")
        Log.d("RetriveLogin", value.toString())


    }
}