package com.example.hp_api.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hp_api.R
import com.example.hp_api.data.interfaces.HPApi
import com.example.hp_api.model.FeiticosModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListSpells : AppCompatActivity() {
    private lateinit var listView: ListView;
    private lateinit var hpApi: HPApi;
    private lateinit var emptyTextView: TextView;
    private val activityContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        enableEdgeToEdge();
        setContentView(R.layout.activity_list_spells);

        listView = findViewById(R.id.resultadoListView);
        emptyTextView = findViewById(R.id.emptyTextView);

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        hpApi = retrofit.create(HPApi::class.java);

        buscarFeiticosApi();

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedChar = parent.getItemAtPosition(position) as FeiticosModel
            val intent = Intent(this, DetailSpells::class.java).apply {
                putExtra("spellID", selectedChar.id)
            }
            startActivity(intent)
        }

    }

    private fun buscarFeiticosApi(){
        lifecycleScope.launch {
            try{
                val spells: List<FeiticosModel> = withContext(Dispatchers.IO) {
                    hpApi.getAllSpells();
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
                hideListView();
                emptyTextView.text = "Erro ao buscar os feitiços";
            }
        }
    }

    fun hideListView(){
        listView.visibility = ListView.GONE;
        emptyTextView.visibility = TextView.VISIBLE;
    }

    fun showListView(){
        listView.visibility = ListView.VISIBLE;
        emptyTextView.visibility = TextView.GONE;
    }
}