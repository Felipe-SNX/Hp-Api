package com.example.hp_api.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hp_api.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom)
            insets
        }

        val btnPersonagem   = findViewById<Button>(R.id.btnPersonagem)
        val btnProfessores  = findViewById<Button>(R.id.btnProfessores)
        val btnEstudantes   = findViewById<Button>(R.id.btnEstudantesCasa)
        val btnFeiticos     = findViewById<Button>(R.id.btnFeiticos)
        val btnSair         = findViewById<Button>(R.id.btnSair)

        btnPersonagem.setOnClickListener {
            startActivity(Intent(this, ListCharacter::class.java))
        }
        btnProfessores.setOnClickListener {
            startActivity(Intent(this, ListTeacher::class.java))
        }
        btnEstudantes.setOnClickListener {
            startActivity(Intent(this, ListStudents::class.java))
        }
        btnFeiticos.setOnClickListener {
            startActivity(Intent(this, ListSpells::class.java))
        }
        btnSair.setOnClickListener { finishAffinity() }
    }
}
