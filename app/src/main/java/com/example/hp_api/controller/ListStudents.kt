package com.example.hp_api.controller

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hp_api.R
import com.example.hp_api.model.PersonagemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class ListStudents : AppCompatActivity() {

    private lateinit var housesGroup: RadioGroup
    private lateinit var resultText: TextView
    private lateinit var btnBuscar: Button

    private val api: HpService by lazy {
        Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HpService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_students)

        housesGroup = findViewById(R.id.housesGroup)
        resultText  = findViewById(R.id.resultText)
        btnBuscar   = findViewById(R.id.btnBuscar)

        btnBuscar.setOnClickListener {
            val checkedId = housesGroup.checkedRadioButtonId
            if (checkedId == -1) {
                Toast.makeText(this, "Escolha uma casa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val house = findViewById<RadioButton>(checkedId).tag.toString()
            fetchStudents(house)
        }
    }

    private fun fetchStudents(house: String) {
        lifecycleScope.launch {
            try {
                val resp = withContext(Dispatchers.IO) { api.studentsByHouse(house) }
                val alunos = resp.filter { it.hogwartsStudent }
                resultText.text = if (alunos.isEmpty()) {
                    "Nenhum aluno encontrado."
                } else {
                    alunos.joinToString("\n") { it.name }
                }
            } catch (e: Exception) {
                Log.e("ListStudents", "Erro API", e)
                Toast.makeText(this@ListStudents, "Falha na consulta", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

interface HpService {
    @GET("api/characters/house/{house}")
    suspend fun studentsByHouse(@Path("house") house: String): List<PersonagemModel>
}
