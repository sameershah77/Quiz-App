package com.example.myquizapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class historyRecyclerviewAdapter(context: Context, arr: ArrayList<String>) : RecyclerView.Adapter<historyRecyclerviewAdapter.Holder>(){
    var context = context
    var arr = arr

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val recycler_coins = itemView.findViewById<TextView>(R.id.earned_coins)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.history_recycler_view_design,parent,false)
        val viewHolder = Holder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.recycler_coins.setText(arr.get(position))
    }
}