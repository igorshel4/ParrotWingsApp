package com.example.parrotwingsapp.UI.UserTransactions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parrotwingsapp.Model.Transaction
import com.example.parrotwingsapp.R

class UserTransactionAdapter(var transactions: List<Transaction>): RecyclerView.Adapter<UserTransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {

        val context = parent.context
        val layoutIdForListNumber = R.layout.user_transaction
        val inflator = LayoutInflater.from(context)
        val view = inflator.inflate(layoutIdForListNumber, parent, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transactions.count()
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class TransactionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        fun bind(listIndex: Int) {
            val tvTransactionDate = itemView.findViewById<TextView>(R.id.tvTransactionDate)
            val tvUserName = itemView.findViewById<TextView>(R.id.tvUserName)
            val tvAmoun = itemView.findViewById<TextView>(R.id.tvAmoun)
            val tvBalance = itemView.findViewById<TextView>(R.id.tvBalance)
            tvTransactionDate.text = transactions[listIndex].date
            tvUserName.text = transactions[listIndex].username
            tvAmoun.text = transactions[listIndex].amount.toString()
            tvBalance.text = transactions[listIndex].balance.toString()
        }
    }
}