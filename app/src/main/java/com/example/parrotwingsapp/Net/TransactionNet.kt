package com.example.parrotwingsapp.Net

import com.example.parrotwingsapp.Application
import com.example.parrotwingsapp.Constants
import com.example.parrotwingsapp.Constants.TRANSACTION_URL
import com.example.parrotwingsapp.Model.Transaction
import com.example.parrotwingsapp.Model.TransactionInfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object TransactionNet {

    private val okHttpClient: OkHttpClient = OkHttpClient()

    fun createTransaction(transactionInfo: TransactionInfo, callback: (Transaction?, e: Exception?) -> Unit) {
        if (Application.tockenId == null) {
            callback(null, Exception(Constants.NOT_AUTORIZED))
            return
        }
        val mediaType = Constants.MEDIA_TYPE.toMediaType()
        val jsonStr = Gson().toJson(transactionInfo)
        val body = jsonStr.toRequestBody(mediaType)
        val request: Request = Request.Builder().url(TRANSACTION_URL).post(body).addHeader(Constants.AUTORIZATION, Constants.BEARER + Application.tockenId!!).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                try {
                    val transactionJson = (JSONObject(json).getJSONObject("trans_token")).toString()
                    val transaction = Gson().fromJson<Transaction>(transactionJson, Transaction::class.java)
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
            callback(null, Exception(Constants.NOT_AUTORIZED))
            return
        }
        val request: Request = Request.Builder().url(TRANSACTION_URL).addHeader(Constants.AUTORIZATION, Constants.BEARER + Application.tockenId!!).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                try {
                    val transactionJson = (JSONObject(json).getJSONArray("trans_token")).toString()
                    var gson = Gson()
                    val sType = object : TypeToken<List<Transaction>>() { }.type
                    val transactionList = gson.fromJson<List<Transaction>>(transactionJson, sType)
                    println("transactionList: $transactionList")
                    callback(transactionList, null)
                }
                catch(e: Exception) {
                    callback(null, e)
                }
            }
        })
    }
}