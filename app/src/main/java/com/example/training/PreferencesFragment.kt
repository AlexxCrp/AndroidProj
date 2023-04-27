package com.example.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import java.util.Locale.*
import kotlin.collections.ArrayList

class PreferencesFragment : Fragment() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newSearchView: SearchView
    private lateinit var newArrayList: ArrayList<SettingsCard>
    lateinit var preferences : Array<String>
    private lateinit var adapter: Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_preferences, container, false)

        preferences = arrayOf(
            "Temperatura",
            "Vreme",
            "Umiditate"
        )

        newRecyclerView = view.findViewById(R.id.recyclerView)
        newSearchView = view.findViewById(R.id.searchView)
        newRecyclerView.layoutManager = LinearLayoutManager(activity)
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

        return view
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
                Toast.makeText(activity, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }
}