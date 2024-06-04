package com.example.myquizapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.RippleDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.translationMatrix
import androidx.core.view.isVisible
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class ScoreBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_board)
        val rootView = findViewById<View>(android.R.id.content)
        rootView.alpha = 0f

        val fadeInDuration = 1000L // Adjust the duration as needed

        rootView.animate()
            .alpha(1f)
            .setDuration(fadeInDuration)
            .setListener(null)
            .start()

        val imageView = findViewById<ImageView>(R.id.imageView)
        // Create ObjectAnimator for rotation along the y-axis
        val rotationAnimator = ObjectAnimator.ofFloat(imageView, "rotationY", 0f, 360f)
        rotationAnimator.duration = 3000 // Set the duration of the rotation in milliseconds
        rotationAnimator.interpolator = AccelerateDecelerateInterpolator() // Set an interpolator for smooth acceleration and deceleration

        // Create ObjectAnimator for scale
        val scaleAnimatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 0.5f, 1f)
        val scaleAnimatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 0f, 0.5f, 1f)
        val scaleAnimatorSet = AnimatorSet()
        scaleAnimatorSet.playTogether(scaleAnimatorX, scaleAnimatorY)
        scaleAnimatorSet.duration = 2000 // Set the duration of the scale animation

        // Create an AnimatorSet to play both animations together
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(rotationAnimator, scaleAnimatorSet)
        // Start the animation
        animatorSet.start()

        val congrats = findViewById<TextView>(R.id.congrats)

        // Create ObjectAnimator for alpha property
        val fadeInAnimator = ObjectAnimator.ofFloat(congrats, "alpha", 0f, 1f)
        fadeInAnimator.duration = 2000 // Set the duration of the fade-in animation in milliseconds
        fadeInAnimator.interpolator = AccelerateDecelerateInterpolator() // Set an interpolator for smooth acceleration and deceleration

        // Create ObjectAnimator for translation along the y-axis
        val translationAnimator = ObjectAnimator.ofFloat(congrats, "translationY", 100f, 0f)
        translationAnimator.duration = 2000 // Set the duration of the translation animation in milliseconds
        translationAnimator.interpolator = AccelerateDecelerateInterpolator() // Set an interpolator for smooth acceleration and deceleration

        // Create AnimatorSet to play both animations together
        val newanimatorSet = AnimatorSet()
        newanimatorSet.playTogether(fadeInAnimator, translationAnimator)
        newanimatorSet.start()

        val medal = findViewById<ImageView>(R.id.medal)
        medal.isVisible = false
        Handler().postDelayed({
            medal.isVisible = true
            // Create ObjectAnimator for translation along the y-axis
            val scaleAnimatorX = ObjectAnimator.ofFloat(medal, "scaleX", 3f, 1.5f, 1f)
            val scaleAnimatorY = ObjectAnimator.ofFloat(medal, "scaleY", 3f, 1.5f, 1f)
            val scaleAnimatorSet = AnimatorSet()
            scaleAnimatorSet.playTogether(scaleAnimatorX, scaleAnimatorY)
            scaleAnimatorSet.duration = 600 // Set the duration of the scale animation
            scaleAnimatorSet.interpolator = AccelerateInterpolator()

            scaleAnimatorSet.start()
        },3000)

        //main work
        val intent = intent
        val correct = findViewById<TextView>(R.id.rightAnswer)
        val wrong = findViewById<TextView>(R.id.wrongAnswer)
        val c = intent.getIntExtra("correct",0)
        val w = intent.getIntExtra("wrong",0)
        val size = c+w
        correct.text = c.toString()+" / "+ size.toString()
        wrong.text = w.toString()+" / "+ size.toString()

        val score = findViewById<TextView>(R.id.score)
        score.text = (intent.getIntExtra("score",-1).toString())
        val totalScore = findViewById<TextView>(R.id.total_score)

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
                    totalScore.text = "${arr.sum()}"

                } else {
                    // No scores found
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors

            }
        })

        val name = findViewById<TextView>(R.id.score_name)
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue<User>()
                    name.setText(user!!.name.toString())
                }
                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
                }
            }
        )


    }

}