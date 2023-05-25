package com.example.training

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private var preferenceList : ArrayList<SettingsCard>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view,
        parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return preferenceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = preferenceList[position]
        holder.preference.text = currentItem.title
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val preference : TextView = itemView.findViewById(R.id.preferenceTitle)
    }

    fun setFilteredList(preferenceList: ArrayList<SettingsCard>){
        this.preferenceList = preferenceList
        notifyDataSetChanged()
    }
}

