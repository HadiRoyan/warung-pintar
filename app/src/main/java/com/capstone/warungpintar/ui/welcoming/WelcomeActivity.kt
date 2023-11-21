package com.capstone.warungpintar.ui.welcoming

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.warungpintar.databinding.ActivityWelcomeBinding
import com.capstone.warungpintar.ui.login.LoginActivity
import com.capstone.warungpintar.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { _ ->
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener { _ ->
            startActivity(Intent(this@WelcomeActivity, RegisterActivity::class.java))
            finish()
        }
    }
}