package com.example.hp_api.data.interfaces

import com.example.hp_api.model.FeiticosModel
import retrofit2.http.GET

interface HPApi {
    @GET("spells")
    suspend fun getAllSpells(): List<FeiticosModel>;
}