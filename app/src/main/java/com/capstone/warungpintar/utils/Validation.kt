package com.capstone.warungpintar.utils

import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object Validation {

    fun validateEmail(
        emailLayout: TextInputLayout,
        emailEditText: TextInputEditText
    ): Boolean {
        if (emailEditText.text.toString().trim { it <= ' ' }.isEmpty()) {
            emailLayout.isErrorEnabled = false
            emailLayout.error = "Email cannot be empty"
            return false
        } else {
            val emailId = emailEditText.text.toString()
            val isValid = Patterns.EMAIL_ADDRESS.matcher(emailId).matches()
            if (!isValid) {
                emailLayout.error = "Invalid Email address, ex: abc@example.com"
                return false
            } else {
                emailLayout.isErrorEnabled = false
            }
        }
        return true
    }

    fun validatePassword(
        passwordLayout: TextInputLayout,
        passwordEditText: TextInputEditText
    ): Boolean {
        if (passwordEditText.text.toString().trim { it <= ' ' }.isEmpty()) {
            passwordLayout.error = "Password required"
            return false
        } else if (passwordEditText.text.toString().length < 8) {
            passwordLayout.errorIconDrawable = null
            passwordLayout.error = "Password can't be less than 8 digit"
            return false
        } else {
            passwordLayout.isErrorEnabled = false
        }
        return true
    }

    fun validateConfirmPassword(
        confirmPasswordLayout: TextInputLayout,
        confirmPasswordEditText: TextInputEditText,
        passwordEditText: TextInputEditText
    ): Boolean {
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()
        if (confirmPasswordEditText.text.toString().trim { it <= ' ' }.isEmpty()) {
            confirmPasswordLayout.error = "Rewrite Password"
            return false
        } else {
            if (password != confirmPassword) {
                confirmPasswordLayout.errorIconDrawable = null
                confirmPasswordLayout.error = "Wrong confirm password"
                return false
            } else {
                confirmPasswordLayout.isErrorEnabled = false
            }
        }
        return true
    }

    fun validateIsNotEmpty(
        name: String,
        layout: TextInputLayout,
        editText: TextInputEditText
    ): Boolean {
        if (editText.text.toString().trim().isEmpty()) {
            layout.error = "$name cannot be empty"
            return false
        } else {
            layout.isErrorEnabled = false
        }
        return true
    }

//test
}