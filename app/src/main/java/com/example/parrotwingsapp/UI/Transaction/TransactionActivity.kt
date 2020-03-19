package com.example.parrotwingsapp.UI.Transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.parrotwingsapp.Model.TransactionInfo
import com.example.parrotwingsapp.Model.UserInfo
import com.example.parrotwingsapp.Net.ProfileNet
import com.example.parrotwingsapp.Net.TransactionNet
import com.example.parrotwingsapp.Net.UserNet
import com.example.parrotwingsapp.R
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        setListeners()


/*        UserNet.getUsers(UserInfo("a"), {l, e ->
            if (e == null) {
                println("Users" + l.toString())
            }
            else {
                println("Error" + e.message)
            }
        })*/

/*        ProfileNet.getProfile({s, e ->
            if (e == null) {
                println("User: " + s.toString())
            }
            else {
                println("Error" + e.message)
            }
        })*/


    }

    private fun setListeners() {

        //addTextChangedListener
        txtUsers.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length != 1) return
                UserNet.getUsers(UserInfo(s.toString()), {list, error ->
                    if (error == null) {
                        runOnUiThread {
                            val users = list?.map { u -> u.name }?.toMutableList()
                            println("users: $users")
                            val adapter = ArrayAdapter<String>(
                                this@TransactionActivity,
                                android.R.layout.simple_dropdown_item_1line,
                                users!!
                            )
                            txtUsers.setAdapter(adapter)
                            txtUsers.threshold = 2
                        }
                    } else {
                        println("Error: $error.message")
                    }
                })

            }
        })


        btnCommit.setOnClickListener {
            val name = txtUsers.text.toString()
            val amoun = txtSum.text.toString().toInt()
            TransactionNet.createTransaction(TransactionInfo(name, amoun)){transaction, error ->
                runOnUiThread {
                    if (error == null) {
                        Toast.makeText(this, transaction.toString(), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}
