package com.example.wegotnext.ui.activities


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.observe
import com.example.wegotnext.R
import com.example.wegotnext.viewmodels.RegistrationViewModel
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationActivity : AppCompatActivity() {
    private val viewModel by viewModel<RegistrationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel.message.observe(this
        ) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()}

        viewModel.registrationSuccess.observe(this) {
            if(it){
                finish()
            }
        }

        setFullScreen()
        setupUI()
    }

    private fun setupUI() {
        tv_login_link.setOnClickListener{onBackPressed()}
        btn_register.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        if (inputIsValid()) {
            val email = et_email_reg.text.toString().trim()
            val password = et_password_reg.text.toString()
            val firstname = et_first_name.text.toString().trim()
            val lastname = et_last_name.text.toString().trim()
            val username = et_username.text.toString().trim()

            viewModel.register(email, password, firstname, lastname, username)
        }
    }

    private fun inputIsValid(): Boolean {

        if(allFieldsChecked()){
            if(!validEmail()){
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show()
                return false
            }

            else if(!validPassword()){
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
                return false
            }
            else{
                return true
            }
        }
        else{
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun validPassword() = et_password_reg.text.toString() == et_confirm_password.text.toString()

    private fun validEmail() = Patterns.EMAIL_ADDRESS.matcher(et_email_reg.text).matches()

    private fun allFieldsChecked(): Boolean {
        return !(et_first_name.text.trim().isEmpty() || et_last_name.text.trim().isEmpty() || et_username.text.trim().isEmpty() ||
                et_email_reg.text.trim().isEmpty() || et_password_reg.text.trim().isEmpty() || et_confirm_password.text.trim().isEmpty())
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