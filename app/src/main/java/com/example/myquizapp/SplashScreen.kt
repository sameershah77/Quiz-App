package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val splash = findViewById<ConstraintLayout>(R.id.splash)
        val animation = AnimationUtils.loadAnimation(this,R.anim.scale)
        splash.startAnimation(animation)

        //FireBase Work
        val FBAuth = FirebaseAuth.getInstance()
        Handler().postDelayed({
            if(FBAuth.currentUser != null) {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            else {
                startActivity(Intent(this,SignUpActivity::class.java))
                finish()
            }
        },2000)



    }
}