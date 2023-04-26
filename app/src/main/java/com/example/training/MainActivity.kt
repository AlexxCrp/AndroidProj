package com.example.training

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import java.util.Locale.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newSearchView: SearchView
    private lateinit var newArrayList: ArrayList<SettingsCard>
    lateinit var preferences : Array<String>
    private lateinit var adapter: Adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = arrayOf(
            "Temperatura",
            "Vreme",
            "Umiditate"
        )

        newRecyclerView = findViewById(R.id.recyclerView)
        newSearchView = findViewById(R.id.searchView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<SettingsCard>()
        adapter = Adapter(newArrayList)
        getUserData()

        newSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun getUserData(){
        for(i in preferences.indices){
            val setting = SettingsCard(preferences[i])
            newArrayList.add(setting)
        }
        newRecyclerView.adapter = adapter
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<SettingsCard>()
            for (i in newArrayList) {
                if (i.title.lowercase(ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }
}