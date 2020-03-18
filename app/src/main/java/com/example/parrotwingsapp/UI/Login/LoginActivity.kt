package com.example.parrotwingsapp.UI.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.parrotwingsapp.Application
import com.example.parrotwingsapp.Model.LoginInfo
import com.example.parrotwingsapp.Net.LoginNet
import com.example.parrotwingsapp.R
import com.example.parrotwingsapp.UI.Registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setListeners()
    }

    fun setListeners() {
        btnLogin.setOnClickListener({
            var email = txtLoginPassword.text.toString().trim()
            var pass = txtLoginEmail.text.toString().trim()
            if (email == "" || pass == "") {
                Toast.makeText(this, R.string.email_pass_empty, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val loginInfo = LoginInfo(email, pass)
            LoginNet.login(loginInfo) { s, e ->
                runOnUiThread {
                    if (e == null) {
                        Application.tockenId = s
                    } else {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

        btnRegistration.setOnClickListener({
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        })
    }
}
