package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import papaya.`in`.sendmail.SendMail
import kotlin.random.Random

class OtpVarificationActivity : AppCompatActivity() {
    lateinit var email : String
    lateinit var pass : String
    lateinit var name : String
    var random: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_varification)
        val intent = intent
        val email_set = findViewById<TextView>(R.id.email_set)
        email = intent.getStringExtra("email").toString()
        pass = intent.getStringExtra("pass").toString()
        name = intent.getStringExtra("name").toString()
        email_set.text = email

        //animation on illustration image
        val illustrationImg = findViewById<ImageView>(R.id.illustrationImg)
        val animation = AnimationUtils.loadAnimation(this,R.anim.translation)
        illustrationImg.startAnimation(animation)

        val otp1 = findViewById<EditText>(R.id.otp1)
        val otp2 = findViewById<EditText>(R.id.otp2)
        val otp3 = findViewById<EditText>(R.id.otp3)
        val otp4 = findViewById<EditText>(R.id.otp4)


        random()


        val resend = findViewById<LinearLayout>(R.id.resend_otp)
        resend.setOnClickListener {
            otp1.text.clear()
            otp2.text.clear()
            otp3.text.clear()
            otp4.text.clear()
            random()
        }

        otp1.doOnTextChanged { text, start, before, count ->
            if(!otp1.text.toString().isEmpty()) {
                otp2.requestFocus()
            }
            if(!otp2.text.toString().isEmpty()) {
                otp2.requestFocus()
            }
        }
        otp2.doOnTextChanged { text, start, before, count ->
            if(!otp2.text.toString().isEmpty()) {
                otp3.requestFocus()
            }else {
                otp1.requestFocus()
            }
        }
        otp3.doOnTextChanged { text, start, before, count ->
            if(!otp3.text.toString().isEmpty()) {
                otp4.requestFocus()
            }else {
                otp2.requestFocus()
            }
        }
        otp4.doOnTextChanged { text, start, before, count ->
            if(!otp4.text.toString().isEmpty()) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(otp4.windowToken, 0)
            }
            val done_otp = findViewById<Button>(R.id.done_otp)
            done_otp.setOnClickListener {
                val Sotp1 = otp1.text.toString()
                val Sotp2 = otp2.text.toString()
                val Sotp3 = otp3.text.toString()
                val Sotp4 = otp4.text.toString()

                val otp = Sotp1+Sotp2+Sotp3+Sotp4

                if(otp1.text.toString().length == 0 && otp2.text.toString().length == 0 && otp3.text.toString().length == 0 && otp4.text.toString().length == 0) {
                    Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show()
                }
                else if(!otp.equals(random.toString())) {
                    Toast.makeText(this, "Wrong OTP", Toast.LENGTH_SHORT).show()
                }
                else {
                    val otp_progressBar = findViewById<ProgressBar>(R.id.otp_progressBar)
                    otp_progressBar.visibility = View.VISIBLE
                    Firebase.auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if(it.isSuccessful) {
                            var user = User(name,email,pass)
                            Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).setValue(user).addOnSuccessListener {
                                val intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                        else {
                            otp_progressBar.visibility = View.INVISIBLE
                            Toast.makeText(this, "Error ! ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
    fun random() {
        random = Random.nextInt(1000,9999)
        val mail = SendMail("sameershah9167@gmail.com","zvjq psol hmbc xukp",email,"OTP Verification","Welcome to Quiz. Complete your email verification by using the following One-Time Password (OTP):\n" +
                "OTP :  ${random}  \n " +
                "If you find this email in your spam/promotion folder, please click on Report Not as Spam.\n" +
                "\n" +
                "If you encounter any issues, please raise a support ticket here.\n" +
                "\n" +
                "This is a system-generated email; please do not reply to this message.")
        mail.execute()
    }

}