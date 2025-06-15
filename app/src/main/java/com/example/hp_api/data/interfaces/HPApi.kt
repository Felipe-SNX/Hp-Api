package com.example.hp_api.data.interfaces

import com.example.hp_api.model.FeiticosModel
import com.example.hp_api.model.PersonagemModel
import retrofit2.http.GET
import retrofit2.http.Path

interface HPApi {
    @GET("spells")
    suspend fun getAllSpells(): List<FeiticosModel>;

    @GET("character/{id}")
    suspend fun getPersonagemID(@Path("id") id: String): List<PersonagemModel>

}