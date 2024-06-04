package com.example.myquizapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class recylerViewAdapter(context: Context, arr: ArrayList<Subject>) : RecyclerView.Adapter<recylerViewAdapter.Holder>() {
    var context = context
    var arr = arr

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recycler_text = itemView.findViewById<TextView>(R.id.recycler_text)
        val recycler_img = itemView.findViewById<ImageView>(R.id.recycler_img)
        val category_item = itemView.findViewById<CardView>(R.id.category_item)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_recycler_view_design,parent,false)
        val viewHolder = Holder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val subName = arr.get(position).category_text
        val subImg = arr.get(position).category_img
        val innerArray = arr.get(position).innerArray
        holder.recycler_text.setText(subName)
        holder.recycler_img.setImageResource(subImg)

        holder.category_item.setOnClickListener {
            val sub_name = subName
            val intent = Intent(context,CatgoryActivity::class.java)

            intent.putExtra("sub_name",sub_name)
            intent.putExtra("sub_details",innerArray)
            context.startActivity(intent)

        }


    }
}