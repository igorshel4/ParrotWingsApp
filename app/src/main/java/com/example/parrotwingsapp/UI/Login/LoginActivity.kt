package com.example.parrotwingsapp.UI.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.parrotwingsapp.Application
import com.example.parrotwingsapp.Model.LoginInfo
import com.example.parrotwingsapp.Net.LoginNet
import com.example.parrotwingsapp.Net.ProfileNet
import com.example.parrotwingsapp.R
import com.example.parrotwingsapp.UI.Registration.RegistrationActivity
import com.example.parrotwingsapp.UI.Transaction.TransactionActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.progressBar

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setListeners()
    }

    fun setListeners() {
        btnLogin.setOnClickListener({
            var email = txtLoginEmail.text.toString().trim()
            var pass = txtLoginPassword.text.toString().trim()
            if (email == "" || pass == "") {
                Toast.makeText(this, R.string.email_pass_empty, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            progressBar.visibility = View.VISIBLE
            val loginInfo = LoginInfo(pass, email)
            LoginNet.login(loginInfo) { s, e ->
                runOnUiThread {
                    if (e == null) {
                        Application.tockenId = s
                        ProfileNet.getProfile {profile, error->
                            runOnUiThread {
                                progressBar.visibility = View.INVISIBLE
                                if (error == null) {
                                    Application.profile = profile
                                    val intent = Intent(this, TransactionActivity::class.java)
                                    startActivity(intent)

                                } else {
                                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        progressBar.visibility = View.INVISIBLE
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
