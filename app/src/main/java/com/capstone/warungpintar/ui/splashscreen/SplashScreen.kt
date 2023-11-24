package com.capstone.warungpintar.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.capstone.warungpintar.R
import com.capstone.warungpintar.preferences.UserPreferences
import com.capstone.warungpintar.ui.main.MainActivity
import com.capstone.warungpintar.ui.welcoming.WelcomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val userPref = UserPreferences.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            val token = userPref.getToken().first()
            if (token.isNullOrEmpty()) {
                startActivity(Intent(this@SplashScreen, WelcomeActivity::class.java))
                finish()
            } else {
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                intent.putExtra("TOKEN", token)
                startActivity(intent)
                finish()
            }
        }
    }
}