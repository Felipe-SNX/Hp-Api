package com.example.hp_api.controller

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.hp_api.R
import com.example.hp_api.data.interfaces.HPApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListStudents : AppCompatActivity() {

    private lateinit var rgHouses: RadioGroup
    private lateinit var btnBuscar: Button
    private lateinit var tvStudents: TextView
    private lateinit var hpApi: HPApi
    private val activityContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_students)

        // Inset padding (mesmo padrão usado na ListSpells)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rgHouses   = findViewById(R.id.rgHouses)
        btnBuscar  = findViewById(R.id.btnBuscar)
        tvStudents = findViewById(R.id.tvStudents)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        hpApi = retrofit.create(HPApi::class.java)

        btnBuscar.setOnClickListener { buscarAlunos() }
    }

    private fun buscarAlunos() {
        val casa = when (rgHouses.checkedRadioButtonId) {
            R.id.rbGryffindor -> "gryffindor"
            R.id.rbSlytherin  -> "slytherin"
            R.id.rbHufflepuff -> "hufflepuff"
            R.id.rbRavenclaw  -> "ravenclaw"
            else              -> ""
        }

        if (casa.isEmpty()) {
            Toast.makeText(this, "Escolha uma casa", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val idRadio=rgHouses.checkedRadioButtonId.toString()

                val personagens = withContext(Dispatchers.IO) {
                    hpApi.getStudentsByHouse("Gryffindor") }

                val nomesAlunos = personagens
                    .filter { it.hogwartsStudent }
                    .map { it.name }

                tvStudents.text = if (nomesAlunos.isEmpty())
                    "Nenhum estudante encontrado" +
                idRadio
                else
                    nomesAlunos.joinToString("\n")

            } catch (e: Exception) {
                Log.e("ListStudents", "Erro na API", e)
                Toast.makeText(this@ListStudents,
                    "Erro ao consultar API",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }
}
