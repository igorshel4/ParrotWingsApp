package com.example.parrotwingsapp.Net

import com.example.parrotwingsapp.Application
import com.example.parrotwingsapp.Model.Profile
import okhttp3.*
import java.io.IOException
import com.example.parrotwingsapp.Constants
import com.google.gson.Gson
import org.json.JSONObject

object ProfileNet {

    var okHttpClient: OkHttpClient = OkHttpClient()

    fun getProfile (callback: (Profile?, e: Exception?) -> Unit) {
        if (Application.tockenId == null) {
//            callback(null, Exception(R.string.not_autorize))
            callback(null, Exception("You don\\'t autorized"))
            return
        }
        val urlStr = Constants.BASE_URL + "/api/protected/user-info"
        val request: Request = Request.Builder().url(urlStr).addHeader("Authorization", "Bearer "+Application.tockenId!!) .build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                try {
                    val profileJson = (JSONObject(json).getJSONObject("user_info_token")).toString()
                    val profile = Gson().fromJson<Profile>(profileJson, Profile::class.java)
                    callback(profile, null)
                }
                catch(e: Exception) {
                    callback(null, Exception(json))
                }
            }
        })
    }

}