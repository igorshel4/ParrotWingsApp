package com.example.parrotwingsapp.Net

import com.example.parrotwingsapp.Constants
import com.example.parrotwingsapp.Model.RegistrationInfo
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object RegistrationNet {

    private var okHttpClient: OkHttpClient = OkHttpClient()

    fun register(registrationInfo: RegistrationInfo, callback: (String?, e: IOException?) -> Unit) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val jsonStr = Gson().toJson(registrationInfo)
        val body = jsonStr.toRequestBody(mediaType)
        val urlStr = Constants.BASE_URL + "/users"
        println("Url: $urlStr")
        val request: Request = Request.Builder().url(urlStr).post(body).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }
            override fun onResponse(call: Call, response: Response) {
                val json = response?.body?.string()
                println("response: $json")
//                val token = JSONObject(json).getJSONObject("id_token").toString()
//                println(token)
                callback(json, null)
            }
        })
    }
}