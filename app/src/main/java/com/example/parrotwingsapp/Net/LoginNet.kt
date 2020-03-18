package com.example.parrotwingsapp.Net

import com.example.parrotwingsapp.Constants
import com.example.parrotwingsapp.Model.LoginInfo
import com.example.parrotwingsapp.Model.RegistrationInfo
import com.example.parrotwingsapp.Model.Token
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object LoginNet {

    private var okHttpClient: OkHttpClient = OkHttpClient()

    fun login(loginInfo: LoginInfo, callback: (String?, e: IOException?) -> Unit) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val jsonStr = Gson().toJson(loginInfo)
        val body = jsonStr.toRequestBody(mediaType)
        val urlStr = Constants.BASE_URL + "/sessions/create"
        println("Url: $urlStr")
        val request: Request = Request.Builder().url(urlStr).post(body).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                var token = Gson().fromJson(json, Token::class.java)
                callback(token.tokenId, null)
            }
        })
    }


}