package com.example.myquizapp

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CatgoryActivity : AppCompatActivity() {
    var i = 0;
    lateinit var ANS : String
    var state = ArrayList<Pair<String,Boolean>>()
    var countCorrect = 0

    var scoreCount = ArrayList<Int>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        val intent = getIntent()
        val item_name = intent.getStringExtra("sub_name")
        val arrQ = intent.getParcelableArrayListExtra<QuestionModel>("sub_details")

        val category_item_name = findViewById<TextView>(R.id.category_item_name)
        category_item_name.setText(item_name)

        val question_desc = findViewById<TextView>(R.id.question_desc)
        val op1 = findViewById<Button>(R.id.op1)
        val op2 = findViewById<Button>(R.id.op2)
        val op3 = findViewById<Button>(R.id.op3)
        val op4 = findViewById<Button>(R.id.op4)
        val total_q = findViewById<TextView>(R.id.total_q)
        val current_q = findViewById<TextView>(R.id.current_q)
        ANS = arrQ?.get(0)?.answer.toString()

        val next_question = findViewById<Button>(R.id.next_question)
        val prev_question = findViewById<Button>(R.id.prev_question)
        total_q.setText(arrQ!!.size.toString())
        val booleanArray = BooleanArray(arrQ!!.size.toInt()) { false }

        Handler().postDelayed({
            question_desc.setBackgroundResource(R.drawable.question_screen_design)
        },1000)

        current_q.setText("${i+1}")

        question_desc.setText(arrQ!!.get(0).question)
        op1.setText(arrQ!!.get(0).option1)
        op2.setText(arrQ!!.get(0).option2)
        op3.setText(arrQ!!.get(0).option3)
        op4.setText(arrQ!!.get(0).option4)



        next_question.setOnClickListener {
            if(booleanArray[i] == false) {
                Toast.makeText(this, "Please select the button", Toast.LENGTH_SHORT).show()
            }
            else {
                animateButton(next_question)
                if(i<arrQ.size-1) {

                    i++
                    current_q.setText("${i+1}")
                    question_desc.setText(arrQ!!.get(i).question)
                    ANS = arrQ?.get(i)?.answer.toString()
                    op1.setText(arrQ!!.get(i).option1)
                    op2.setText(arrQ!!.get(i).option2)
                    op3.setText(arrQ!!.get(i).option3)
                    op4.setText(arrQ!!.get(i).option4)

                    op1.setBackgroundResource(R.drawable.option_design)
                    op2.setBackgroundResource(R.drawable.option_design)
                    op3.setBackgroundResource(R.drawable.option_design)
                    op4.setBackgroundResource(R.drawable.option_design)

                    op1.isEnabled = true
                    op2.isEnabled = true
                    op3.isEnabled = true
                    op4.isEnabled = true

                    question_desc.setBackgroundResource(R.drawable.question_screen_design_glolw)
                    Handler().postDelayed({
                        question_desc.setBackgroundResource(R.drawable.question_screen_design)
                    },1000)
                }
                else {

                    Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).child("Scores").push().setValue(countCorrect*10)

                    Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).child("Scores").addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            // Handle data change
                            if (dataSnapshot.exists()) {
                                scoreCount.clear()
                                val arr = ArrayList<Int>()
                                for (scoreSnapshot in dataSnapshot.children) {
                                    val score = scoreSnapshot.getValue(Int::class.java)?.toInt()
                                    if (score != null) {
                                        arr.add(score)
                                    }
                                }
                                scoreCount = arr


                            } else {
                                // No scores found
                            }

                            Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).child("TotalScore").setValue(scoreCount.sum())
                        }
                        override fun onCancelled(databaseError: DatabaseError) {
                            // Handle errors

                        }
                    })
                    var totalSum = 0
                    Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).child("TotalScore").addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            totalSum += snapshot.getValue(Int::class.java)!!
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })

                    val intent = Intent(this,ScoreBoardActivity::class.java)
                    val wrong = arrQ.size - countCorrect
                    intent.putExtra("correct",countCorrect)
                    intent.putExtra("wrong",wrong)
                    intent.putExtra("score",countCorrect*10)
                    intent.putExtra("totalscore",totalSum)

                    startActivity(intent)
                    finish()
                }
            }
        }

        prev_question.setOnClickListener {
            animateButton(prev_question)

            if(i > 0) {
                --i
                current_q.setText("${i+1}")
                question_desc.setText(arrQ!!.get(i).question)
                ANS = arrQ?.get(i)?.answer.toString()
                op1.setText(arrQ!!.get(i).option1)
                op2.setText(arrQ!!.get(i).option2)
                op3.setText(arrQ!!.get(i).option3)
                op4.setText(arrQ!!.get(i).option4)

                question_desc.setBackgroundResource(R.drawable.question_screen_design_glolw)
                Handler().postDelayed({
                    question_desc.setBackgroundResource(R.drawable.question_screen_design)
                },500)
                op1.setBackgroundResource(R.drawable.option_design)
                op2.setBackgroundResource(R.drawable.option_design)
                op3.setBackgroundResource(R.drawable.option_design)
                op4.setBackgroundResource(R.drawable.option_design)

                checkState(op1,op2,op3,op4)

            }
            else {
                Toast.makeText(this, "Press back to leave Quiz", Toast.LENGTH_SHORT).show()
            }
        }



        op1.setOnClickListener {
            optionButtonAnimation(op1)
            op2.isEnabled = false
            op3.isEnabled = false
            op4.isEnabled = false
            booleanArray[i] = true

        }

        op2.setOnClickListener {
            optionButtonAnimation(op2)
            op1.isEnabled = false
            op3.isEnabled = false
            op4.isEnabled = false
            booleanArray[i] = true
        }

        op3.setOnClickListener {
            optionButtonAnimation(op3)
            op2.isEnabled = false
            op1.isEnabled = false
            op4.isEnabled = false
            booleanArray[i] = true
        }

        op4.setOnClickListener {
            optionButtonAnimation(op4)
            op2.isEnabled = false
            op3.isEnabled = false
            op1.isEnabled = false
            booleanArray[i] = true
        }
    }

    private fun optionButtonAnimation(button: Button) {
        val next_question = findViewById<Button>(R.id.next_question)
        next_question.isEnabled = false
        button.setBackgroundResource(R.drawable.selected)
        Handler().postDelayed({

            if(button.text == ANS) {
                button.setBackgroundResource(R.drawable.selected_correct)
                state.add(Pair(button.text.toString(),true))
                countCorrect++
            }
            else {
                button.setBackgroundResource(R.drawable.selected_wrong)
                state.add(Pair(button.text.toString(),false))
            }
            next_question.isEnabled = true
        },500)
    }

    fun checkState(op1: Button,op2: Button,op3: Button,op4: Button) {
        if(op1.text == state.get(i).first) {
            if(state.get(i).second == true) {
                op1.setBackgroundResource(R.drawable.selected_correct)
            }
            else {
                op1.setBackgroundResource(R.drawable.selected_wrong)
            }
        }
        else if(op2.text == state.get(i).first) {
            if(state.get(i).second == true) {
                op2.setBackgroundResource(R.drawable.selected_correct)
            }
            else {
                op2.setBackgroundResource(R.drawable.selected_wrong)
            }
        }
        else if(op3.text == state.get(i).first) {
            if(state.get(i).second == true) {
                op3.setBackgroundResource(R.drawable.selected_correct)
            }
            else {
                op3.setBackgroundResource(R.drawable.selected_wrong)
            }
        }
        else  {
            if(state.get(i).second == true) {
                op4.setBackgroundResource(R.drawable.selected_correct)
            }
            else {
                op4.setBackgroundResource(R.drawable.selected_wrong)
            }
        }
    }
    private fun animateButton(button: View) {
        // Create an ObjectAnimator to scale the button
        val scaleX = ObjectAnimator.ofFloat(button, View.SCALE_X, 1f, 1.02f, 1f)
        val scaleY = ObjectAnimator.ofFloat(button, View.SCALE_Y, 1f, 1.02f, 1f)
        scaleX.duration = 500
        scaleY.duration = 500

        val view = button
        val fadeIn = ObjectAnimator.ofFloat(view, View.ALPHA, 0.8f, 1f)
        fadeIn.duration = 1000


        // Create a ViewPropertyAnimator to animate the elevation property
        view.animate()
            .setDuration(500)
            .translationZBy(20f) // Adjust the shadow elevation change
            .withEndAction {
                // Reset the elevation after the animation completes
                view.translationZ = 0f
            }

        // Start the animations
        scaleX.start()
        scaleY.start()
        fadeIn.start()
    }
}


