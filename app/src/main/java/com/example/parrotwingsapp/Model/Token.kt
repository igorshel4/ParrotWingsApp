package com.example.parrotwingsapp.Model

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("id_token")
    var tokenId: String? = null
)