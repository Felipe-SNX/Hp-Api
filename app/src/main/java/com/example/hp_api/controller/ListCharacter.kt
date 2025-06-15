package com.example.hp_api.controller

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.hp_api.R

class ListCharacter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_character)

        val etPersonagemID = findViewById<EditText>(R.id.etPersonagemID)
        val textViewName = findViewById<TextView>(R.id.textViewName)
        val textViewCasa = findViewById<TextView>(R.id.textViewCasa)
        val textViewEspecie = findViewById<TextView>(R.id.textViewEspecie)
        val imageViewPersonagem = findViewById<ImageView>(R.id.imageViewPersonagem)
        val bntBuscar = findViewById<Button>(R.id.bntBuscar)

        /*bntBuscar.setOnClickListener

            buscaPersonagemID
            val personagemID = editTextPersonagemID.text.toString()

            if (personagemID.isBlank()) {
                Toast.makeText(this, "Por favor, insira um ID de personagem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.getPersonagem()
            }
        }*/



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}