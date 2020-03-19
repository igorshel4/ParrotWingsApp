package com.example.parrotwingsapp.Net

import com.example.parrotwingsapp.Application
import com.example.parrotwingsapp.Constants
import com.example.parrotwingsapp.Model.User
import com.example.parrotwingsapp.Model.UserInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object UserNet {

    private val okHttpClient: OkHttpClient = OkHttpClient()

    fun getUsers(userInfo: UserInfo, callback: (List<User>?, e: Exception?) -> Unit) {
        if (Application.tockenId == null) {
            callback(null, Exception("You don\'t autorized"))
            return
        }
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val jsonStr = Gson().toJson(userInfo)
        val body = jsonStr.toRequestBody(mediaType)
        val urlStr = Constants.BASE_URL + "/api/protected/users/list"
        val request: Request = Request.Builder().url(urlStr).addHeader("Authorization", "Bearer " + Application.tockenId!!).post(body).build()
        okHttpClient.newCall(request).enqueue(object: Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val gson = Gson()
                val sType = object : TypeToken<List<User>>() {}.type
                try {
                    val userList = gson.fromJson<List<User>>(json, sType)
                    callback(userList, null)
                } catch (e: Exception) {
                    callback(null, Exception(json))
                }
            }
        })
    }
}