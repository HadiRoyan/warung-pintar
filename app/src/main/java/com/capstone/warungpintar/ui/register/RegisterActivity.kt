package com.capstone.warungpintar.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.warungpintar.R
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.databinding.ActivityRegisterBinding
import com.capstone.warungpintar.ui.login.LoginActivity
import com.capstone.warungpintar.utils.Validation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var nameEditText: TextInputEditText
    private lateinit var nameLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var confirmPasswordLayout: TextInputLayout

    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        setupAction()

        viewModel.registerResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        showMessage("berhasil mendaftar, silakan masuk")
                        // TODO: wait for the API
                        // gotoLogin()
                        // viewModel.registerResult.removeObservers(this)
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        showMessage(result.error)
                    }
                }
            }
        }
    }

    private fun setupViews() {
        nameLayout = binding.layoutInputName
        nameEditText = binding.edRegisName
        emailLayout = binding.layoutInputEmail
        emailEditText = binding.edRegisEmail
        passwordLayout = binding.layoutInputPassword
        passwordEditText = binding.edRegisPassword
        confirmPasswordLayout = binding.layoutInputConfirmPassword
        confirmPasswordEditText = binding.edRegisConfirmPassword

        nameEditText.addTextChangedListener(
            ValidationTextWatcher(
                binding.root,
                nameEditText
            )
        )
        emailEditText.addTextChangedListener(
            ValidationTextWatcher(
                binding.root, emailEditText
            )
        )
        passwordEditText.addTextChangedListener(
            ValidationTextWatcher(
                binding.root,
                passwordEditText
            )
        )
        confirmPasswordEditText.addTextChangedListener(
            ValidationTextWatcher(
                binding.root,
                confirmPasswordEditText
            )
        )
    }

    private fun setupAction() {
        binding.btnSignup.setOnClickListener { _ ->
            if (validate()) {
                // TODO: Not yet connected to the API, still waiting from the CC team

                // val name = nameEditText.text.toString().trim()
                // val email = emailEditText.text.toString().trim()
                // val password = passwordEditText.text.toString().trim()
                // viewModel.register(name, email, password)

                // Move directly to the LoginActivity for testing
                gotoLogin()
                showMessage("[TESTING] berhasil mendaftar, silakan masuk")
            } else {
                showMessage("Masukan valid data")
            }
        }

        binding.btnLogin.setOnClickListener { _ ->
            gotoLogin()
        }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                gotoLogin()
            }
        })
    }

    private fun gotoLogin() {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        finish()
    }

    private fun validate(): Boolean {
        val isNameValid = Validation.validateIsNotEmpty("Name", nameLayout, nameEditText)
        val isEmailValid = Validation.validateEmail(emailLayout, emailEditText)
        val isPasswordValid = Validation.validatePassword(passwordLayout, passwordEditText)

        val isConfirmPasswordValid = Validation.validateConfirmPassword(
            confirmPasswordLayout,
            confirmPasswordEditText,
            passwordEditText
        )

        return (isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid)
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
            val nameLayout: TextInputLayout = view.findViewById(R.id.layout_input_name)
            val nameEditText: TextInputEditText = view.findViewById(R.id.ed_regis_name)
            val emailEditText: TextInputEditText = view.findViewById(R.id.ed_regis_email)
            val emailLayout: TextInputLayout = view.findViewById(R.id.layout_input_email)
            val passwordEditText: TextInputEditText = view.findViewById(R.id.ed_regis_password)
            val passwordLayout: TextInputLayout = view.findViewById(R.id.layout_input_password)
            val confirmPasswordEditText: TextInputEditText =
                view.findViewById(R.id.ed_regis_confirm_password)
            val confirmPasswordLayout: TextInputLayout =
                view.findViewById(R.id.layout_input_confirm_password)

            when (viewToValidate.id) {
                R.id.ed_regis_password -> Validation.validatePassword(
                    passwordLayout,
                    passwordEditText
                )

                R.id.ed_regis_confirm_password -> Validation.validateConfirmPassword(
                    confirmPasswordLayout,
                    confirmPasswordEditText,
                    passwordEditText
                )

                R.id.ed_regis_email -> Validation.validateEmail(
                    emailLayout,
                    emailEditText
                )

                R.id.ed_regis_name -> Validation.validateIsNotEmpty(
                    "Name",
                    nameLayout,
                    nameEditText
                )
            }
        }

        override fun afterTextChanged(editable: Editable) {}
    }
}