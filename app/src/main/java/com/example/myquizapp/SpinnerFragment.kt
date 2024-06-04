package com.example.myquizapp

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Objects
import kotlin.random.Random
import kotlin.random.nextInt

class SpinnerFragment : BottomSheetDialogFragment() {

    lateinit var timer:CountDownTimer
    var itemTitles = arrayOf("500","Try Again","100","200","Try Again","100","200","Try Again")
    lateinit var spinButton :Button
    lateinit var wheelImageView : ImageView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_spinner, container, false)
        spinButton = view.findViewById(R.id.spin_btn)
        wheelImageView = view.findViewById(R.id.spinner)

        spinButton.setOnClickListener {
            spinButton.isEnabled = false
            val spin = Random.nextInt(8)
            val degrees=360f*2 + 45f*spin

            val animator = ObjectAnimator.ofFloat(wheelImageView, View.ROTATION, 360+degrees)
            animator.duration = 1000 // Adjust the duration as needed
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.addUpdateListener { animation ->
                wheelImageView.rotation = animation.animatedValue as Float
            }
            animator.start()

            animator.addListener(object : android.animation.Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    showResult(itemTitles[spin])
                }
                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
        }

        return view
    }

    fun showResult(item: String) {
        spinButton.isEnabled = true
        val intent = Intent(requireContext(),SpinScoreBoardActivity::class.java)
        intent.putExtra("score","${item}")
        startActivity(intent)

    }

}