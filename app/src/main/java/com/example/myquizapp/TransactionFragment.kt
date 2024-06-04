package com.example.myquizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TransactionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tansaction, container, false)
        // Inflate the layout for this fragment
        val transaction_name = view.findViewById<TextView>(R.id.transaction_name)

            Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user = snapshot.getValue<User>()
                        transaction_name.setText(user!!.name.toString())
                    }
                    override fun onCancelled(error: DatabaseError) {
//
                    }
                }
            )

        val transaction_score = view.findViewById<TextView>(R.id.transaction_score)
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
                    transaction_score.text = "${arr.sum()}"

                } else {
                    // No scores found
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors

            }
        })



        return view
    }

}