package com.example.hp_api.utils

import com.example.hp_api.data.interfaces.HPApi
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://hp-api.onrender.com/api/"

    private val gson = GsonBuilder()
        .setDateFormat("dd-MM-yyyy")
        .create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: HPApi by lazy {
        retrofit.create(HPApi::class.java)
    }
}
