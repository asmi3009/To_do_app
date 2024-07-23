package com.example.task_to_do

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Goals : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goals)
        sharedPreferences = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        listView = findViewById(R.id.list_view)
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

        val eventCount = sharedPreferences.getInt("event_count", 0)
        val eventList = ArrayList<String>()

        for (i in 0 until eventCount) {
            val eventName = sharedPreferences.getString("Event name$i", "")
            val eventDate = sharedPreferences.getString("Event Date$i", "")

            if (eventName != null && eventDate != null) {
                val currentDate = getCurrentDate()
                if (currentDate != null) {
                    if (compareDates(eventDate, currentDate)) {
                        eventList.add(eventName)
                    }
                } else {
                    Log.e("Goals", "Current date is null")
                }
            } else {
                Log.e("Goals", "Event name or date is null")
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, eventList)
        listView.adapter = adapter
    }

    private fun getCurrentDate(): String? {
        try {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            return "$dayOfMonth/${month + 1}/$year"
        } catch (e: Exception) {
            Log.e("Goals", "Error getting current date", e)
            return null
        }
    }

    private fun compareDates(eventDate: String, currentDate: String): Boolean {
        try {
            val eventDateParts = eventDate.split("/")
            val currentDateParts = currentDate.split("/")

            val eventDay = eventDateParts[0].toInt()
            val eventMonth = eventDateParts[1].toInt()
            val eventYear = eventDateParts[2].toInt()

            val currentDay = currentDateParts[0].toInt()
            val currentMonth = currentDateParts[1].toInt()
            val currentYear = currentDateParts[2].toInt()

            if (eventYear < currentYear) {
                return true
            } else if (eventYear == currentYear) {
                if (eventMonth < currentMonth) {
                    return true
                } else if (eventMonth == currentMonth) {
                    if (eventDay <= currentDay) {
                        return true
                    }
                }
            }

            return false
        } catch (e: Exception) {
            Log.e("Goals", "Error comparing dates", e)
            return false
        }
    }
}