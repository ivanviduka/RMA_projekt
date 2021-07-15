package com.example.wegotnext.ui.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.observe
import com.example.wegotnext.R
import com.example.wegotnext.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.message.observe(this
        ) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()}

        viewModel.loginSuccess.observe(this) {
            if(it){
                showMainActivity()
            }
        }

        setFullScreen()
        setupUI()
    }

    private fun setupUI() {
        tv_forgot_password.setOnClickListener{ showNewPassword()}
        tv_register_link.setOnClickListener { showRegistration() }
        btn_login.setOnClickListener { loginUser() }

    }

    private fun loginUser() {
        if (inputIsValid()) {
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString()

            viewModel.login(email, password)
        }
    }

    private fun inputIsValid(): Boolean {
        if (allFieldsChecked()) {

            if (!validEmail()) {
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show()
                return false

            } else return true

        } else {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun validEmail() = Patterns.EMAIL_ADDRESS.matcher(et_email.text).matches()

    private fun allFieldsChecked(): Boolean {
        return !(et_email.text.trim().isEmpty() || et_password.text.trim().isEmpty())
    }

    private fun showMainActivity(){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showRegistration() {
        val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun showNewPassword() {
        val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun setFullScreen() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}
