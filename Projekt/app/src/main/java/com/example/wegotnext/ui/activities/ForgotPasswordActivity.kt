package com.example.wegotnext.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.observe
import com.example.wegotnext.R
import com.example.wegotnext.viewmodels.ForgotPasswordViewModel
import kotlinx.android.synthetic.main.activity_forgot_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordActivity : AppCompatActivity() {

    private val viewModel by viewModel<ForgotPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        viewModel.message.observe(this
        ) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()}

        viewModel.passwordSendSuccess.observe(this) {
            if(it){
                finish()
            }
        }
        setupUI()
    }

    private fun setupUI() {
        btn_submit.setOnClickListener{sendNewPassword()}
    }

    private fun sendNewPassword() {
        if (inputIsValid()) {
            val email = et_email_forgot.text.toString().trim()
            viewModel.send(email)
        }
    }

    private fun inputIsValid(): Boolean {
        if (!validEmail()) {
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show()
            return false

        } else return true
    }

    private fun validEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(et_email_forgot.text).matches() && et_email_forgot.text.toString().trim().isNotEmpty()
    }
}