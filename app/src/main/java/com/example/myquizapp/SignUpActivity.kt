package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val log_in = findViewById<LinearLayout>(R.id.log_in)
        log_in.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
            finish()
        }

        val sign_up_name = findViewById<EditText>(R.id.sign_up_name)
        val sign_up_email = findViewById<EditText>(R.id.sign_up_email)
        val sign_up_password = findViewById<EditText>(R.id.sign_up_password)
        val sign_up_confirm_password = findViewById<EditText>(R.id.sign_up_confirm_password)


        updateGlowOfEditText(sign_up_name)
        updateGlowOfEditText(sign_up_email)
        updateGlowOfEditText(sign_up_password)
        updateGlowOfEditText(sign_up_confirm_password)
        val parent = findViewById<View>(R.id.parent)
        parent.setOnClickListener {
            hideKeyboardAndFocuse(sign_up_name)
            hideKeyboardAndFocuse(sign_up_email)
            hideKeyboardAndFocuse(sign_up_password)
            hideKeyboardAndFocuse(sign_up_confirm_password)
        }


        val submit = findViewById<Button>(R.id.submit)
        submit.setOnClickListener {
            val name = sign_up_name.text.toString()
            val email = sign_up_email.text.toString()
            val pass1 = sign_up_password.text.toString()
            val pass2 = sign_up_confirm_password.text.toString()
            if(name.length == 0 && email.length == 0 && pass1.length == 0 && pass2.length == 0) {
                Toast.makeText(this, "Please Fill All the Fields", Toast.LENGTH_SHORT).show()
            }
            else if(name.length == 0) {
                Toast.makeText(this, "Please Fill Name", Toast.LENGTH_SHORT).show()
            }else if(email.length == 0) {
                Toast.makeText(this, "Please Fill Email", Toast.LENGTH_SHORT).show()
            }else if(pass1.length == 0) {
                Toast.makeText(this, "Please Fill password", Toast.LENGTH_SHORT).show()
            }else if(pass2.length == 0) {
                Toast.makeText(this, "Please Fill confirm password", Toast.LENGTH_SHORT).show()
            }else if(pass1!= pass2) {
                Toast.makeText(this, "Password are doesn't match", Toast.LENGTH_SHORT).show()
            }else if(pass1 == pass2 && pass1.length < 8) {
                Toast.makeText(this, "Password should contains at least 8 digits", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this,OtpVarificationActivity::class.java)
                intent.putExtra("email",email)
                intent.putExtra("pass",pass1)
                intent.putExtra("name",name)
                startActivity(intent)
                finish()
            }

        }
    }

    private fun updateGlowOfEditText(address: EditText) {
        address.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus == true) {
                address.setBackgroundResource(R.drawable.new_edit_text_design)
            }
            else {
                address.setBackgroundResource(R.drawable.edit_text_design)
            }
        }
    }

    private fun hideKeyboardAndFocuse(address: EditText) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(address.windowToken, 0)
        address.clearFocus()
    }

}