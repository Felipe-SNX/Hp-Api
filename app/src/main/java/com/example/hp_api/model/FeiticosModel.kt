package com.example.hp_api.model

data class FeiticosModel(
    val id: String,
    val name: String,
    val description: String
){
    override fun toString(): String {
        return this.name
    }
}
