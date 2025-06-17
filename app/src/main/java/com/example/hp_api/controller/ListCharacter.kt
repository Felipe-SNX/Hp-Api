package com.example.hp_api.controller

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hp_api.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import coil.load
import com.example.hp_api.model.PersonagemModel
import com.example.hp_api.utils.RetrofitClient


class ListCharacter : AppCompatActivity() {


    private lateinit var etPersonagemID: EditText
    private lateinit var textViewName: TextView
    private lateinit var textViewCasa: TextView
    private lateinit var textViewEspecie: TextView
    private lateinit var imageViewPersonagem: ImageView
    private lateinit var btnBuscar: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_character)

        etPersonagemID = findViewById<EditText>(R.id.etStaffName)
        textViewName = findViewById<TextView>(R.id.textViewName)
        textViewCasa = findViewById<TextView>(R.id.textViewCasa)
        textViewEspecie = findViewById<TextView>(R.id.textViewEspecie)
        imageViewPersonagem = findViewById<ImageView>(R.id.imageViewPersonagem)
        btnBuscar = findViewById<Button>(R.id.btnBuscar)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        loadingTextView = findViewById<TextView>(R.id.loadingTextView)


        btnBuscar.setOnClickListener {
            buscarPersonagemIDApi()
        }

    }

    private fun buscarPersonagemIDApi() {

        lifecycleScope.launch {
            try {

                val personagemID = etPersonagemID.text.toString()

                if (!validateFields(personagemID))
                    return@launch

                showProgressBar()

                val response = buscarPersonagemAPI(personagemID)
                val personagem = response?.firstOrNull()

                if (personagem != null) {

                    preencherCampos(personagem)
                    showCharacter()

                } else {
                    Toast.makeText(
                        this@ListCharacter,
                        "Personagem não encontrado.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ListCharacter,
                    "Não foi possivel buscar o personagem, tente novamente.",
                    Toast.LENGTH_LONG
                ).show()
                Log.e("ListCharacter", "Erro ao buscar o personagem", e)
            }
            finally {
                hideProgressBar()
            }
        }
    }

    private fun showProgressBar() {
        progressBar.visibility = ProgressBar.VISIBLE
        loadingTextView.visibility = TextView.VISIBLE
        textViewName.visibility = TextView.GONE
        textViewCasa.visibility = TextView.GONE
        textViewEspecie.visibility = TextView.GONE
        imageViewPersonagem.visibility = ImageView.GONE
    }

    private fun hideProgressBar() {
        progressBar.visibility = ProgressBar.GONE
        loadingTextView.visibility = TextView.GONE
    }

    private fun showCharacter(){
        textViewName.visibility = TextView.VISIBLE
        textViewCasa.visibility = TextView.VISIBLE
        textViewEspecie.visibility = TextView.VISIBLE
        imageViewPersonagem.visibility = ImageView.VISIBLE
    }

    private fun preencherCampos(personagemID: PersonagemModel) {
        textViewName.text = "Nome: ${personagemID.name}"
        textViewEspecie.text = "Espécie: ${personagemID.species}"
        textViewCasa.text = "Casa: ${personagemID.house}"

        imageViewPersonagem.load(personagemID.image) {
            error(R.drawable.ic_launcher_foreground)
        }

    }

    private fun validateFields(personagemID: String) : Boolean {

        if (personagemID.isBlank()) {
            Toast.makeText(this, "Por favor, insira um ID de personagem", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private suspend fun buscarPersonagemAPI(personagemID: String) : List<PersonagemModel>? {
        return try {
            withContext(Dispatchers.IO) {
                RetrofitClient.apiService.getPersonagemID(personagemID)
            }
        }
        catch (e: Exception) {
            Toast.makeText(
                this@ListCharacter,
                "Não foi possivel buscar o personagem, tente novamente.",
                Toast.LENGTH_LONG
            ).show()
            Log.e("ListCharacter", "Erro ao buscar o personagem", e)
            null
        }
    }
}