package com.example.hp_api.data.interfaces

import com.example.hp_api.model.FeiticosModel
import com.example.hp_api.model.PersonagemModel
import retrofit2.http.Path
import retrofit2.http.GET

interface HPApi {
    @GET("spells")
    suspend fun getAllSpells(): List<FeiticosModel>;

    @GET("characters/house/{house}")
    suspend fun getStudentsByHouse(
        @Path("house") house: String
    ): List<PersonagemModel>

    @GET("character/{id}")
    suspend fun getPersonagemID(@Path("id") id: String): List<PersonagemModel>

    @GET("characters/staff")
    suspend fun getAllStaff(): List<PersonagemModel>

}