package com.capstone.warungpintar.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.warungpintar.R
import com.capstone.warungpintar.ui.dashboard.DashboardProduct
import com.capstone.warungpintar.ui.welcoming.WelcomeActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val auth = Firebase.auth


        Handler(Looper.getMainLooper()).postDelayed({
            val user = auth.currentUser
            if (user != null) {
                val intent = Intent(this@SplashScreen, DashboardProduct::class.java)
                intent.putExtra("TOKEN", user.uid)
                startActivity(intent)
                finish()
            } else {
                startActivity(Intent(this@SplashScreen, WelcomeActivity::class.java))
                finish()
            }
        }, 500L)


//        val userPref = UserPreferences.getInstance(this)
//        lifecycleScope.launch {
//            val token = userPref.getToken().first()
//            if (token.isNullOrEmpty()) {
//                startActivity(Intent(this@SplashScreen, WelcomeActivity::class.java))
//                finish()
//            } else {
//                val intent = Intent(this@SplashScreen, MainActivity::class.java)
//                intent.putExtra("TOKEN", token)
//                startActivity(intent)
//                finish()
//            }
//        }
    }
}