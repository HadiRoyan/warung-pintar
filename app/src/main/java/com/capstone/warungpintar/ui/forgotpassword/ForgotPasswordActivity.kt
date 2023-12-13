package com.capstone.warungpintar.ui.forgotpassword

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.warungpintar.databinding.ActivityForgotPasswordBinding
import com.capstone.warungpintar.utils.Validation
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnResetPassword.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val isEmailValid = Validation.validateEmail(
            binding.emailLayout,
            binding.emailEditText
        )

        if (isEmailValid) {
            showLoading(true)
            val email = binding.emailEditText.text.toString().trim()
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    showMessage("Link telah dikirim melalui email")
                }
                .addOnFailureListener { exception ->
                    exception.message?.let { message ->
                        showMessage(message)
                    } ?: showMessage("Gagal mengirim email untuk mengatur ulang password")
                }
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun showMessage(message: String) {
        Toast.makeText(this@ForgotPasswordActivity, message, Toast.LENGTH_SHORT).show()
    }
}