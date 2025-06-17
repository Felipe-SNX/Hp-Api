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
import com.example.hp_api.data.interfaces.HPApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import coil.load
import com.example.hp_api.model.PersonagemModel


class ListCharacter : AppCompatActivity() {


    private lateinit var etPersonagemID: EditText
    private lateinit var textViewName: TextView
    private lateinit var textViewCasa: TextView
    private lateinit var textViewEspecie: TextView
    private lateinit var imageViewPersonagem: ImageView
    private lateinit var btnBuscar: Button
    private lateinit var hpApi: HPApi
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingTextView: TextView
    private val personagemID = ""


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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        hpApi = retrofit.create(HPApi::class.java)

        btnBuscar.setOnClickListener {
            val personagemID = etPersonagemID.text.toString()

            if (personagemID.isBlank()) {
                Toast.makeText(this, "Por favor, insira um ID de personagem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            buscarPersonagemIDApi(personagemID)

        }

    }

    private fun buscarPersonagemIDApi(personagemID: String) {

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    hpApi.getPersonagemID(personagemID)
                }
                val personagemID = response.firstOrNull()

                if (personagemID != null) {

                    System.out.println(personagemID.name)

                    textViewName.text = "Nome: ${personagemID.name}"
                    textViewEspecie.text = "Espécie: ${personagemID.species}"
                    textViewCasa.text = "Casa: ${personagemID.house}"


                   imageViewPersonagem.load(personagemID.image) {
                       error(R.drawable.ic_launcher_foreground)
                   }

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
                    "Falha na busca: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                Log.e("ListCharacter", "Erro ao buscar o personagem", e)
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

    private fun showStaff(){
        textViewName.visibility = TextView.VISIBLE
        textViewCasa.visibility = TextView.VISIBLE
        textViewEspecie.visibility = TextView.VISIBLE
        imageViewPersonagem.visibility = ImageView.VISIBLE
    }

    /*private fun preencherCampos(staff: PersonagemModel) {
        textViewName.text = "Nome: ${staff.name}"
        textViewEspecie.text = "Espécie: ${staff.species}"
        textViewCasa.text = "Casa: ${staff.house}"
        textViewAlternateNames.text = "Apelidos: ${staff.alternate_names.joinToString(", ")}"

        imageViewPersonagem.load(staff.image) {
            error(R.drawable.ic_launcher_foreground)
        }

    }*/

}