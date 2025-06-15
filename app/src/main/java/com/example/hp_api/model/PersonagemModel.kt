package com.example.hp_api.model

import java.util.Date

data class PersonagemModel(
    val id: String,
    val name: String,
    val alternate_names: List<String>,
    val species: String,
    val gender: String,
    val dateOfBirth: Date,
    val yearOfBirth: Int,
    val wizard: Boolean,
    val ancestry: String,
    val eyeColor: String,
    val hairColor: String,
    val wand: VarinhaModel,
    val patronus: String,
    val hogwartsStudent: Boolean,
    val hogwartsStaff: Boolean,
    val actor: String,
    val alternate_actors: List<String>,
    val alive: Boolean,
    val image: String,
)
