package com.example.parrotwingsapp.UI.Transaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.parrotwingsapp.Application
import com.example.parrotwingsapp.Model.TransactionInfo
import com.example.parrotwingsapp.Model.UserInfo
import com.example.parrotwingsapp.Net.TransactionNet
import com.example.parrotwingsapp.Net.UserNet
import com.example.parrotwingsapp.R
import com.example.parrotwingsapp.UI.Login.LoginActivity
import com.example.parrotwingsapp.UI.UserTransactions.UserTransactionsActivity
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        var toolBar: Toolbar = toolbar as Toolbar
        toolBar.setTitle("${Application.profile?.name}, Balance: ${Application.profile?.balance}")
        setSupportActionBar(toolBar)
        setListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id =  item.itemId
        if (R.id.action_new_transaction == id) {
            return true
        } else if (R.id.action_transactions == id){
            val intent = Intent(this, UserTransactionsActivity::class.java)
            startActivity(intent)
            return true
        }
        else if (R.id.action_logout == id){
            Application.tockenId = null
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
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
                progressBar.visibility = View.VISIBLE
                UserNet.getUsers(UserInfo(s.toString()), {list, error ->
                    runOnUiThread {
                        progressBar.visibility = View.INVISIBLE
                        if (error == null) {
                            val users = list?.map { u -> u.name }?.toMutableList()
                            if (users != null) {
                                val adapter = ArrayAdapter<String>(
                                    this@TransactionActivity,
                                    android.R.layout.simple_dropdown_item_1line,
                                    users
                                )
                                txtUsers.setAdapter(adapter)
                                txtUsers.threshold = 2
                            }

                        } else {
                            println("Error: $error.message")
                        }
                    }
                })

            }
        })


        btnCommit.setOnClickListener {
            val name = txtUsers.text.toString().trim()
            val amount = txtSum.text.toString().trim().toIntOrNull()
            val balance = Application.profile?.balance
            if (balance == null) {
                return@setOnClickListener
            }
            if (amount == null
                || amount <= 0
                || amount > balance) {
                Toast.makeText(this, R.string.incorrect_amount, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (name == "") {
                Toast.makeText(this, R.string.empty_name, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            progressBar.visibility = View.VISIBLE
            TransactionNet.createTransaction(TransactionInfo(name, amount)){transaction, error ->
                runOnUiThread {
                    progressBar.visibility = View.INVISIBLE
                    if (error == null) {
                        val intent = Intent(this, UserTransactionsActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}
