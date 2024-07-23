package com.example.task_to_do

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class completion_task : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var etGoal: EditText
    private lateinit var lvGoals: ListView
    private lateinit var btnAddGoal: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_completion_task)

        toolbar = findViewById(R.id.toolbar) // Initialize toolbar
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.main)

        val navigationView = findViewById<NavigationView>(R.id.navbar)
        if (navigationView == null) {
            Log.e("Error", "NavigationView not found")
            return
        }

        sharedPreferences = getSharedPreferences("my_pref", Context.MODE_PRIVATE)

        navigationView.bringToFront()

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
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

        etGoal = findViewById(R.id.et_goal)
        btnAddGoal = findViewById(R.id.btn_add_goal)
        lvGoals = findViewById(R.id.lv_goals)

        if (etGoal == null || btnAddGoal == null || lvGoals == null) {
            Log.e("Error", "Views not found")
            return
        }

        sharedPreferences = getSharedPreferences("GOALS_PREF", MODE_PRIVATE)

        val goals =
            sharedPreferences.getString("GOALS", "")?.split(",")?.toMutableList()?: mutableListOf()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, goals)
        lvGoals.adapter = adapter

        btnAddGoal.setOnClickListener {
            val goal = etGoal.text.toString()
            if (goal.isNotEmpty()) {
                addGoalToSharedPreferences(goal)
                etGoal.setText("")
                Toast.makeText(this, "Goal added!", Toast.LENGTH_SHORT).show()
                adapter.add(goal)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Please enter a goal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addGoalToSharedPreferences(goal: String) {
        val editor = sharedPreferences.edit()
        val existingGoals = sharedPreferences.getString("GOALS", "")
        editor.putString("GOALS", "$existingGoals,$goal")
        editor.apply()
    }
}