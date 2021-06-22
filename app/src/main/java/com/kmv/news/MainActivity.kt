package com.kmv.news

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        val homeFragment = HomeFragment()
        val firstFragment = SportFragment()
        val secondFragment = EntertainmentFragment()
        val thirdFragment = HealthFragment()
        val fourthFragment = TechnologyFragment()
     //   val fiveFragment = ScienceFragment()

        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
               R.id.home -> setCurrentFragment(homeFragment)
                R.id.sport -> setCurrentFragment(firstFragment)
                R.id.entertainment -> setCurrentFragment(secondFragment)
                R.id.health -> setCurrentFragment(thirdFragment)
                R.id.technology -> setCurrentFragment(fourthFragment)
          //      R.id.science -> setCurrentFragment(fiveFragment)

            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}