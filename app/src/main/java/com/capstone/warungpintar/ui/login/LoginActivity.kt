package com.capstone.warungpintar.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.warungpintar.R
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.databinding.ActivityLoginBinding
import com.capstone.warungpintar.ui.main.MainActivity
import com.capstone.warungpintar.ui.register.RegisterActivity
import com.capstone.warungpintar.utils.Validation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var emailEditText: TextInputEditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var passwordLayout: TextInputLayout

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        setupAction()

        viewModel.loginResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        Log.d(TAG, "login success: ${result.data.token}")

                        // TODO: waiting for API from CC team to test real login
                        /*
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                        viewModel.loginResult.removeObservers(this)
                        */
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        Log.d(TAG, "login error: ${result.error}")
                    }
                }
            }
        }
    }

    private fun setupViews() {
        emailLayout = binding.layoutInputEmail
        emailEditText = binding.edEmail
        passwordLayout = binding.layoutInputPassword
        passwordEditText = binding.edPassword

        // text watcher
        emailEditText.addTextChangedListener(ValidationTextWatcher(binding.root, emailEditText))
        passwordEditText.addTextChangedListener(
            ValidationTextWatcher(
                binding.root,
                passwordEditText
            )
        )
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener { _ ->
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener { _ ->
            val isEmailValid = Validation.validateEmail(emailLayout, emailEditText)
            val isPasswordValid = Validation.validatePassword(passwordLayout, passwordEditText)

            if (isEmailValid && isPasswordValid) {
                // TODO: Not yet connected to the API, still waiting from the CC team
//                viewModel.login(
//                    emailEditText.text.toString().trim(),
//                    passwordEditText.text.toString().trim()
//                )
                showMessage("Login Success [Testing]")
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {
                showMessage("Email or password cannot be empty")
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private class ValidationTextWatcher(private val view: View, private val viewToValidate: View) :
        TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            val emailEditText: TextInputEditText = view.findViewById(R.id.ed_email)
            val emailLayout: TextInputLayout = view.findViewById(R.id.layout_input_email)
            val passwordEditText: TextInputEditText = view.findViewById(R.id.edPassword)
            val passwordLayout: TextInputLayout = view.findViewById(R.id.layout_input_password)

            when (viewToValidate.id) {
                R.id.ed_email -> Validation.validateEmail(
                    emailLayout,
                    emailEditText
                )

                R.id.edPassword -> Validation.validatePassword(
                    passwordLayout,
                    passwordEditText
                )
            }
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}