package com.example.task_to_do

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class first_screen : AppCompatActivity(){

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_first_screen)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.main)
        val navigationView = findViewById<NavigationView>(R.id.navbar)
        val addbtt = findViewById<Button>(R.id.add_task)
        val taskcount = findViewById<TextView>(R.id.total_task)
        val user = findViewById<TextView>(R.id.hi)
        val alltask = findViewById<Button>(R.id.comp)

        sharedPreferences = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

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
        val eventCount = sharedPreferences.getInt("event_count", 0)
        taskcount.text = "$eventCount"
        addbtt.setOnClickListener(){
            intent= Intent(applicationContext, Addtask::class.java)
            startActivity(intent)
        }
        val userName = sharedPreferences.getString("user_name", "")
        user.text = "Hi $userName"

        alltask.setOnClickListener(){
            val intent = Intent(applicationContext, Alltask::class.java)
            startActivity(intent)
        }




    }




}