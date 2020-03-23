package com.example.parrotwingsapp

import com.example.parrotwingsapp.Application

object Constants {
    val BASE_URL = "http://193.124.114.46:3001"
    val LOGIN_URL = "$BASE_URL/sessions/create"
    val REGISTRATION_URL = "$BASE_URL/users"
    val PROFILE_URL = "$BASE_URL/api/protected/user-info"
    val TRANSACTION_URL = "$BASE_URL/api/protected/transactions"
    val USER_URL = "$BASE_URL/api/protected/users/list"
    val AUTORIZATION = "Authorization"
    val BEARER = "Bearer "
    val MEDIA_TYPE = "application/json; charset=utf-8"
    val NOT_AUTORIZED = "You don\'t autorized"

}