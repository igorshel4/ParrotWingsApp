package com.example.parrotwingsapp.Net

import android.view.View
import com.example.parrotwingsapp.Model.Profile
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import com.example.parrotwingsapp.Constants

object ProfileNet {

    var okHttpClient: OkHttpClient = OkHttpClient()


    fun getProfile (): Profile? {
        return null
    }

}