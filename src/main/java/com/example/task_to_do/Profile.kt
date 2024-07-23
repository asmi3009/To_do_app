package com.example.task_to_do

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Profile : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tvName: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvPhoneNumber: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE)

        tvName = findViewById(R.id.username)
        tvDate = findViewById(R.id.date)
        tvAddress = findViewById(R.id.address)
        tvPhoneNumber = findViewById(R.id.phone)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.main)
        val navigationView = findViewById<NavigationView>(R.id.navbar)
        navigationView.bringToFront()
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    val intent = Intent(applicationContext, first_screen::class.java)
                    startActivity(intent)
                }

                R.id.goal -> {
                    val intent = Intent(applicationContext, completion_task::class.java)
                    startActivity(intent)
                }
                R.id.pendng ->{
                    val intent = Intent(applicationContext,Goals::class.java)
                    startActivity(intent)
                }
                R.id.details ->{
                    val intent = Intent(applicationContext, Profile::class.java)
                    startActivity(intent)
                }
                R.id.linkedin -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("www.linkedin.com/in/asmita-halder-839b2b217")
                    startActivity(intent)
                }


                R.id.facebook -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("https://www.facebook.com/asmita.halder.524")
                    startActivity(intent)
                }

                R.id.insta -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("https://www.instagram.com/asmi5670/")
                    startActivity(intent)
                }

            }
            true
        }

        val userName = sharedPreferences.getString("user_name", "")
        val userDate = sharedPreferences.getString("user_date", "")
        val userAddress = sharedPreferences.getString("user_address", "")
        val userPhoneNumber = sharedPreferences.getString("user_phone_number", "")

        tvName.text = "Name: $userName"
        tvDate.text = "Date: $userDate"
        tvAddress.text = "Address: $userAddress"
        tvPhoneNumber.text = "Phone Number: $userPhoneNumber"
    }
}