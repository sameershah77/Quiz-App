package com.example.myquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(HomeFragment())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setOnItemSelectedListener {item->
            if(item.itemId == R.id.nav_home) {
                replaceFragment(HomeFragment())
            }
            else if(item.itemId == R.id.nav_share) {
                replaceFragment(TransactionFragment())
            }
            else if(item.itemId == R.id.nav_profile) {
                replaceFragment(ProfileFragment())
            }
            else {
                replaceFragment(HistoryFragment())
            }
            true
        }

        val nav_spin = findViewById<FloatingActionButton>(R.id.nav_spin)
        nav_spin.setOnClickListener {
            val bottomSheetDialog : BottomSheetDialogFragment = SpinnerFragment()
            bottomSheetDialog.show(supportFragmentManager,"TEST")
            bottomSheetDialog.enterTransition
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragManager = supportFragmentManager
        val fragTransaction = fragManager.beginTransaction()
        fragTransaction.replace(R.id.frame_layout,fragment)
        fragTransaction.commit()
    }


}