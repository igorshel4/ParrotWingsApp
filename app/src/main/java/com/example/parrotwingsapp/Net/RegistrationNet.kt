package com.example.parrotwingsapp.Net

import com.example.parrotwingsapp.Constants
import com.example.parrotwingsapp.Model.RegistrationInfo
import com.example.parrotwingsapp.Model.Token
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object RegistrationNet {

    private var okHttpClient: OkHttpClient = OkHttpClient()

    fun register(registrationInfo: RegistrationInfo, callback: (String?, e: Exception?) -> Unit) {
        val mediaType = Constants.MEDIA_TYPE.toMediaType()
        val jsonStr = Gson().toJson(registrationInfo)
        val body = jsonStr.toRequestBody(mediaType)
        val request: Request = Request.Builder().url(Constants.REGISTRATION_URL).post(body).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                try {
                    val token = Gson().fromJson(json, Token::class.java)
                    callback(token.tokenId, null)
                }
                catch(e: Exception) {
                    callback(null, Exception(json))
                }
            }
        })
    }
}