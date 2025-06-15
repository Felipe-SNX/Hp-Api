package com.example.hp_api.controller

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.example.hp_api.R
import com.example.hp_api.model.FeiticosModel

class DetailSpell : AppCompatActivity() {

    private lateinit var dadosFeitico: FeiticosModel
    private lateinit var nameSpellTextView: TextView;
    private lateinit var descriptionSpellTextView: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_spell)

        nameSpellTextView = findViewById(R.id.nameSpellTextView);
        descriptionSpellTextView = findViewById(R.id.descriptionSpellTextView);

        recebeDadosFeitico();
        insereDadosFeitico();
    }

    private fun recebeDadosFeitico(){
        val bundle = intent.extras
        if(bundle != null) {
            val feitico = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("feitico", FeiticosModel::class.java)
            } else {
                bundle.getParcelable("feitico");
            }
            if(feitico != null) dadosFeitico = feitico
        }
    }

    private fun insereDadosFeitico(){
        val nameSpell = dadosFeitico.name;
        val descriptionSpell = dadosFeitico.description;

        nameSpellTextView.text = buildSpannedString {
            bold {
                append("Nome: ")
            }
            append(nameSpell)
        }

        descriptionSpellTextView.text = buildSpannedString {
            bold {
                append("Descrição: ")
            }
            append(descriptionSpell)
        }
    }
}