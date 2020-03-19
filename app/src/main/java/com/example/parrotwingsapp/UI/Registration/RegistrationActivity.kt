package com.example.parrotwingsapp.UI.Registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.parrotwingsapp.Application
import com.example.parrotwingsapp.Model.RegistrationInfo
import com.example.parrotwingsapp.R
import com.example.parrotwingsapp.Net.*
import com.example.parrotwingsapp.UI.Transaction.TransactionActivity
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setListeners()
    }

    fun setListeners() {
        btnPrfRegistr.setOnClickListener({
            val email = txtRegEmail.text.toString().trim()
            val pass = txtRegRassword.text.toString().trim()
            val userName = txtUserName.text.toString().trim()
            val repPass = txtRepitRassword.text.toString().trim()
            if (userName == "") {
                Toast.makeText(this, R.string.user_name_empty, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (email == "" || pass == "") {
                Toast.makeText(this, R.string.email_pass_empty, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pass != repPass) {
                Toast.makeText(this, R.string.pass_not_equal_rep_pass, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val registrationInfo = RegistrationInfo(txtUserName.text.toString(), txtRegRassword.text.toString(), txtRegEmail.text.toString())
            RegistrationNet.register(registrationInfo) { s, e ->
                runOnUiThread {
                    if (e == null) {
                        Application.tockenId = s
                        val intent = Intent(this, TransactionActivity::class.java)
                        startActivity(intent)                    }
                    else {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
