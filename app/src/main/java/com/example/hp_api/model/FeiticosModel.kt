package com.example.hp_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeiticosModel(
    val id: String,
    val name: String,
    val description: String
): Parcelable
{
    override fun toString(): String {
        return this.name
    }
}
