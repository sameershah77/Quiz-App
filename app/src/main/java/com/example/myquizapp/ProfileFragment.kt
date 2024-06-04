package com.example.myquizapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val logoutBtn = view.findViewById<Button>(R.id.logout)
        logoutBtn.setOnClickListener {
            val logoutDialog = Dialog(requireContext())
            logoutDialog.setContentView(R.layout.alert_dialog_desgin)
                val no = logoutDialog.findViewById<Button>(R.id.logout_no)
                val yes = logoutDialog.findViewById<Button>(R.id.logout_yes)

            yes.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(requireContext(),SignInActivity::class.java))
                requireActivity().finish()
                logoutDialog.dismiss()
            }
            no.setOnClickListener {
                logoutDialog.dismiss()
            }
            logoutDialog.show()
        }
        val name = view.findViewById<TextView>(R.id.profile_name)
        val nameTop = view.findViewById<TextView>(R.id.profile_name_top)
        val email = view.findViewById<TextView>(R.id.profile_email)
        val pass = view.findViewById<TextView>(R.id.profile_password)
        val age = view.findViewById<TextView>(R.id.profile_age)
        updateProfile(name,nameTop,email,pass)


        val edit_profile_btn = view.findViewById<ImageView>(R.id.edit_profile_btn)
        edit_profile_btn.setOnClickListener {
            val editProfile = Dialog(requireContext())
            editProfile.setContentView(R.layout.dialog_box)
            val editName = editProfile.findViewById<EditText>(R.id.editname)
            val editAge = editProfile.findViewById<EditText>(R.id.editAge)
            val submitbtn = editProfile.findViewById<Button>(R.id.submitbtn)

            editName.setText(name.text.toString())
            editAge.setText(age.text.toString())

            submitbtn.setOnClickListener {
                if(editName.text.toString().length == 0 && editAge.text.toString().length == 0) {
                    Toast.makeText(requireContext(), "Please Fill all fields", Toast.LENGTH_SHORT).show()
                }
                else {
                    var user = User(editName.text.toString(),email.text.toString(),pass.text.toString())
                    Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).setValue(user).addOnSuccessListener {
                        editProfile.dismiss()
                    }
                    age.setText(editAge.text.toString())
                    updateProfile(name,nameTop,email,pass)
                }
            }
            editProfile.show()
        }
        return view
    }

    fun updateProfile(name: TextView, nameTop: TextView, email: TextView, pass: TextView) {
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue<User>()
                    name.setText(user!!.name.toString())
                    nameTop.setText(user!!.name.toString())
                    email.setText(user!!.email.toString())
                    pass.setText(user!!.password.toString())
                }
                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
                }
            }
        )
    }

}