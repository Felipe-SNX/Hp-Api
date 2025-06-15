package com.example.hp_api.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hp_api.R
import com.example.hp_api.data.interfaces.HPApi
import com.example.hp_api.model.FeiticosModel
import com.example.hp_api.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListSpells : AppCompatActivity() {

    private lateinit var titleTextView: TextView;
    private lateinit var listView: ListView;
    private lateinit var loadingTextView: TextView;
    private lateinit var progressBar: ProgressBar;
    private val activityContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        enableEdgeToEdge();
        setContentView(R.layout.activity_list_spells);

        listView = findViewById(R.id.resultadoListView);
        loadingTextView = findViewById(R.id.loadingTextView);
        progressBar = findViewById(R.id.progressBar);
        titleTextView = findViewById(R.id.titleTextView);

        buscarFeiticosApi();

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedChar = parent.getItemAtPosition(position) as FeiticosModel
            val intent = Intent(this, DetailSpell::class.java).apply {
                putExtra("feitico", selectedChar);
            }
            startActivity(intent)
        }

    }

    private fun buscarFeiticosApi(){
        lifecycleScope.launch {
            try{
                val spells: List<FeiticosModel> = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.getAllSpells()
                }

                val adapter = ArrayAdapter(
                    activityContext,
                    android.R.layout.simple_list_item_1,
                    spells
                )
                listView.adapter = adapter
                showListView();
            }
            catch (e: Exception){
                Log.e("ListSpells", "Erro ao buscar os feiticos", e);
                hideProgressBar();
                loadingTextView.text = "Ocorreu um erro ao buscar os feitiços";
            }
        }
    }

    fun hideProgressBar(){
        progressBar.visibility = ProgressBar.GONE;
    }

    fun showListView(){
        loadingTextView.visibility = TextView.GONE;
        progressBar.visibility = ProgressBar.GONE;
        titleTextView.visibility = TextView.VISIBLE;
        listView.visibility = ListView.VISIBLE;
    }
}