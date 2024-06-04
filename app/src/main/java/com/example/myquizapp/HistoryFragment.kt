package com.example.myquizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HistoryFragment : Fragment() {
    val arr = ArrayList<String>()
    lateinit var adapter: historyRecyclerviewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val history_name = view.findViewById<TextView>(R.id.history_name)
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue<User>()
                    history_name.setText(user!!.name.toString())
                }
                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
                }
            }
        )

        val history_score = view.findViewById<TextView>(R.id.history_score)
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).child("Scores").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Handle data change
                if (dataSnapshot.exists()) {
                    val arr = ArrayList<Int>()
                    for (scoreSnapshot in dataSnapshot.children) {
                        val score = scoreSnapshot.getValue(Int::class.java)?.toInt()
                        if (score != null) {
                            arr.add(score)
                        }
                    }
                    history_score.text = "${arr.sum()}"
                } else {
                    // No scores found
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors

            }
        })


        val recylerView = view.findViewById<RecyclerView>(R.id.history_recyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        recylerView.layoutManager = layoutManager

        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).child("Scores").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Handle data change
                if (dataSnapshot.exists()) {
                    val tempArr = ArrayList<Int>()
                    arr.clear()
                    for (scoreSnapshot in dataSnapshot.children) {
                        val score = scoreSnapshot.getValue(Int::class.java)?.toInt()
                        if (score != null) {
                            arr.add("${score}")
                        }
                    }
                    arr.reverse()
                    adapter.notifyDataSetChanged()
                    adapter.notifyItemInserted(arr.size-1)
                } else {
                    // No scores found
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {


            }
        })

        adapter = historyRecyclerviewAdapter(requireContext(),arr)

        recylerView.adapter = adapter



        return view
    }

}