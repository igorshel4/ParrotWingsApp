package com.example.parrotwingsapp.UI.UserTransactions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parrotwingsapp.Application
import com.example.parrotwingsapp.Net.TransactionNet
import com.example.parrotwingsapp.R
import com.example.parrotwingsapp.UI.Login.LoginActivity
import com.example.parrotwingsapp.UI.Transaction.TransactionActivity
import kotlinx.android.synthetic.main.activity_user_transactions.*
import kotlinx.android.synthetic.main.activity_user_transactions.toolbar

class UserTransactionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_transactions)

        var toolBar: Toolbar = toolbar as Toolbar
        toolBar.setTitle("${Application.profile?.name}, Balance: ${Application.profile?.balance}")
        setSupportActionBar(toolBar)

        progressBar.visibility = View.VISIBLE
        TransactionNet.getTransactions() { l, e ->
            runOnUiThread {
                progressBar.visibility = View.INVISIBLE
                if (e == null) {

                    var transactionsAdapor = UserTransactionAdapter(l!!, this)
                    val layoutManager = LinearLayoutManager(this)
                    rvTransacions.layoutManager = layoutManager
                    rvTransacions.setHasFixedSize(true)
                    rvTransacions.adapter = transactionsAdapor
                } else {
                    println("Error:" + e.message)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id =  item.itemId
        if (R.id.action_new_transaction == id) {
            val intent = Intent(this, TransactionActivity::class.java)
            startActivity(intent)
            return true
        } else if (R.id.action_transactions == id){
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
}
