package com.example.hp_api.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.hp_api.R

class MainActivity : AppCompatActivity() {
    private lateinit var buttonListSpells: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        buttonListSpells = findViewById(R.id.listSpells);

        buttonListSpells.setOnClickListener {
            startActivity(Intent(this, ListSpells::class.java))
        }
    }
}