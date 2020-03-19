package com.example.parrotwingsapp.Net

import com.example.parrotwingsapp.Application
import com.example.parrotwingsapp.Constants
import com.example.parrotwingsapp.Model.Token
import com.example.parrotwingsapp.Model.Transaction
import com.example.parrotwingsapp.Model.TransactionInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object TransactionNet {

    private val okHttpClient: OkHttpClient = OkHttpClient()

    fun createTransaction(transactionInfo: TransactionInfo, callback: (Transaction?, e: Exception?) -> Unit) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val jsonStr = Gson().toJson(transactionInfo)
        println("body $jsonStr")
        val body = jsonStr.toRequestBody(mediaType)
        val urlStr = Constants.BASE_URL + "/api/protected/transactions"
        val request: Request = Request.Builder().url(urlStr).post(body).addHeader("Authorization", "Bearer " + Application.tockenId!!).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                try {
                    val transaction = Gson().fromJson(json, Transaction::class.java)
                    callback(transaction, null)
                }
                catch(e: Exception) {
                    callback(null, Exception(json))
                }
            }
        })

    }

    fun getTransactions(callback: (List<Transaction>?, e: Exception?) -> Unit) {
        if (Application.tockenId == null) {
            callback(null, Exception("You don\'t autorized"))
            return
        }
        val urlStr = Constants.BASE_URL + "/api/protected/transactions"
        val request: Request = Request.Builder().url(urlStr).addHeader("Authorization", "Bearer " + Application.tockenId!!).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val gson = Gson()
                val sType = object : TypeToken<List<Transaction>>() { }.type
                try {
                    val transactionList = gson.fromJson<List<Transaction>>(json, sType)
                    callback(transactionList, null)
                }
                catch(e: Exception) {
                    callback(null, Exception(json))
                }
            }
        })
    }
}