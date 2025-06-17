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


class ListTeacher : AppCompatActivity() {

    private lateinit var etStaffName: EditText
    private lateinit var textViewName: TextView
    private lateinit var textViewCasa: TextView
    private lateinit var textViewEspecie: TextView
    private lateinit var textViewAlternateNames: TextView
    private lateinit var imageViewPersonagem: ImageView
    private lateinit var btnBuscar: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_teacher)

        etStaffName = findViewById<EditText>(R.id.etStaffName)
        textViewName = findViewById<TextView>(R.id.textViewName)
        textViewCasa = findViewById<TextView>(R.id.textViewCasa)
        textViewEspecie = findViewById<TextView>(R.id.textViewEspecie)
        textViewAlternateNames = findViewById<TextView>(R.id.textViewAlternateNames)
        imageViewPersonagem = findViewById<ImageView>(R.id.imageViewPersonagem)
        btnBuscar = findViewById<Button>(R.id.btnBuscar)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        loadingTextView = findViewById<TextView>(R.id.loadingTextView)

        btnBuscar.setOnClickListener {
            buscarStaff()
        }
    }

    private suspend fun buscarStaffApi() : List<PersonagemModel>? {

        return try {
            withContext(Dispatchers.IO) {
                RetrofitClient.apiService.getAllStaff()
            }
        }
        catch (e: Exception) {
            Toast.makeText(
                this@ListTeacher,
                "Não foi possivel buscar o professor, tente novamente.",
                Toast.LENGTH_LONG
            ).show()
            Log.e("ListTeacher", "Erro ao buscar o professor", e)
            null
        }
    }

    private fun buscarStaff() {

        lifecycleScope.launch {

            if (!validateFields())
                return@launch

            showProgressBar()

            val response = buscarStaffApi()
            val staffName = etStaffName.text.toString()
            //System.out.println(response)
            buscarStaffPorNome(staffName, response)

            hideProgressBar()
        }
    }

    private fun showProgressBar() {
        progressBar.visibility = ProgressBar.VISIBLE
        loadingTextView.visibility = TextView.VISIBLE
        textViewName.visibility = TextView.GONE
        textViewCasa.visibility = TextView.GONE
        textViewEspecie.visibility = TextView.GONE
        textViewAlternateNames.visibility = TextView.GONE
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
        textViewAlternateNames.visibility = TextView.VISIBLE
        imageViewPersonagem.visibility = ImageView.VISIBLE
    }

    private fun preencherCampos(staff: PersonagemModel) {
        textViewName.text = "Nome: ${staff.name}"
        textViewEspecie.text = "Espécie: ${staff.species}"
        textViewCasa.text = "Casa: ${staff.house}"
        textViewAlternateNames.text = "Apelidos: ${staff.alternate_names.joinToString(", ")}"

        imageViewPersonagem.load(staff.image) {
            error(R.drawable.ic_launcher_foreground)
        }

    }

    private fun validateFields() :Boolean {
        val staffName = etStaffName.text.toString()

        if (staffName.isBlank()) {
            Toast.makeText(this, "Insira um nome de professor", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun buscarStaffPorNome(staffName: String, response: List<PersonagemModel>?) {

        if (response != null && response.isNotEmpty()) {

            for (staff in response) {

                if (staff.name.equals(staffName, ignoreCase = true)) {
                    preencherCampos(staff)
                    showStaff()
                    return
                }
            }
            Toast.makeText(
                this@ListTeacher,
                "Professor não encontrado.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}