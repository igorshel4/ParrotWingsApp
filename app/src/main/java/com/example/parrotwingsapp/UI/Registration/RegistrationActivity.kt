package com.example.parrotwingsapp.UI.Registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.parrotwingsapp.Model.RegistrationInfo
import com.example.parrotwingsapp.R
import com.example.parrotwingsapp.Net.*
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setListeners()
    }

    fun setListeners() {
        btnPrfRegistr.setOnClickListener({
            val registrationInfo = RegistrationInfo(txtUserName.text.toString(), txtRegRassword.text.toString(), txtRegEmail.text.toString())
            RegistrationNet.register(registrationInfo) { s, e ->
                runOnUiThread {
                    if (e == null) {
                        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
