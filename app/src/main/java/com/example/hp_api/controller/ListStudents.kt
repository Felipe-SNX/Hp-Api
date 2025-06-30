package com.example.hp_api.controller

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hp_api.R
import com.example.hp_api.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListStudents : AppCompatActivity() {

    private lateinit var housesGroup: RadioGroup
    private lateinit var resultText: TextView
    private lateinit var btnBuscar: Button
    private val api = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_students)
        housesGroup = findViewById(R.id.housesGroup)
        resultText = findViewById(R.id.resultText)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnBuscar.setOnClickListener {
            validarCampos()?.let { casa -> buscarAlunos(casa) }
        }
    }

    private fun validarCampos(): String? {
        val checkedId = housesGroup.checkedRadioButtonId
        if (checkedId == -1) {
            Toast.makeText(this, "Escolha uma casa", Toast.LENGTH_SHORT).show()
            return null
        }
        return findViewById<RadioButton>(checkedId).tag.toString()
    }

    private fun buscarAlunos(house: String) {
        lifecycleScope.launch {
            try {
                val resp = withContext(Dispatchers.IO) { api.getStudentsByHouse(house) }
                val alunos = resp.filter { it.hogwartsStudent }
                resultText.text = if (alunos.isEmpty()) {
                    "Nenhum aluno encontrado."
                } else {
                    alunos.joinToString(separator = "\n") { it.name }
                }
            } catch (e: Exception) {
                Log.e("ListStudents", "Erro API", e)
                Toast.makeText(this@ListStudents, "Falha na consulta", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
