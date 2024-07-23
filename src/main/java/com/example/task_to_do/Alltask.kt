package com.example.task_to_do

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Alltask : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var taskListView : ListView
    private lateinit var taskArray: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alltask)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.main)
        sharedPreferences = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        taskListView = findViewById(R.id.task_list)

        taskArray = ArrayList<String>()
        val etCount = sharedPreferences.getInt("event_count", 0)
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
                R.id.nav_home ->{
                    val completedCount = countCompletedTasks()
                    val sharedPreferences = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putInt("completed_count", completedCount)
                    editor.apply()
                    intent = Intent(applicationContext,first_screen::class.java)
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
        taskListView.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, view, position, _ ->
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Event Options")
            alertDialog.setMessage("Is this event completed?")

            alertDialog.setPositiveButton("Yes") { _, _ ->
                markTaskAsCompleted(position)
                taskArray[position] = "Event Name: ${taskArray[position].split("\n")[0].split(": ")[1]}\nEvent Description: ${taskArray[position].split("\n")[1].split(": ")[1]}\nEvent Date: ${taskArray[position].split("\n")[2].split(": ")[1]}\nEvent Time: ${taskArray[position].split("\n")[3].split(": ")[1]}\nCompleted"
                adapter.notifyDataSetChanged()
            }

            alertDialog.setNegativeButton("No") { _, _ ->
                // do nothing
            }

            alertDialog.show()
            true
        }

        for (i in 0 until etCount) {
            val eventName = sharedPreferences.getString("Event name$i", "")
            val eventDescription = sharedPreferences.getString("Event Decription$i", "")
            val eventDate = sharedPreferences.getString("Event Date$i", "")
            var eventTime = sharedPreferences.getString("Event Time$i", "")
            if (sharedPreferences.getBoolean("Task $i completed", false)) {
                eventTime += " (Completed)"
            }

            taskArray.add("Event Name: $eventName\nEvent Description: $eventDescription\nEvent Date: $eventDate\nEvent Time: $eventTime")
        }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, taskArray)
        taskListView.adapter = adapter
        taskListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val eventId = position
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Event Options")
            alertDialog.setMessage("Choose an option for this event")

            alertDialog.setPositiveButton("Delete") { _, _ ->
                deleteEvent(eventId)
                taskArray.removeAt(position)
                adapter.notifyDataSetChanged()
            }

            alertDialog.setNeutralButton("Edit") { _, _ ->
                editEvent(eventId)
            }

            alertDialog.setNegativeButton("Cancel") { _, _ ->
                // do nothing
            }

            alertDialog.show()
        }
    }

    private fun countCompletedTasks(): Int {
        val sharedPreferences = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        val eventCount = sharedPreferences.getInt("event_count", 0)
        var completedCount = 0

        for (i in 0 until eventCount) {
            if (sharedPreferences.getBoolean("Task $i completed", false)) {
                completedCount++
            }
        }

        return completedCount
    }

    private fun markTaskAsCompleted(position: Int) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("Task $position completed", true)
        editor.apply()

        val eventName = taskArray[position].split("\n")[0].split(": ")[1]
        val eventDescription = taskArray[position].split("\n")[1].split(": ")[1]
        val eventDate = taskArray[position].split("\n")[2].split(": ")[1]
        val eventTime = taskArray[position].split("\n")[3].split(": ")[1]

        editor.putString("Completed Event name$position", eventName)
        editor.putString("Completed Event Decription$position", eventDescription)
        editor.putString("Completed Event Date$position", eventDate)
        editor.putString("Completed Event Time$position", eventTime)
        editor.apply()

        // Remove the task from shared preferences
        editor.remove("Task $position")
        editor.apply()

        // Remove the task from the taskArray and update the UI
        taskArray.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    private fun deleteEvent(eventId: Int) {
        val editor = sharedPreferences.edit()
        val eventCount = sharedPreferences.getInt("event_count", 0)

        for (i in eventId until eventCount - 1) {
            editor.putString("Event name$i", sharedPreferences.getString("Event name${i + 1}", ""))
            editor.putString("Event Decription$i", sharedPreferences.getString("Event Decription${i + 1}", ""))
            editor.putString("Event Date$i", sharedPreferences.getString("Event Date${i + 1}", ""))
            editor.putString("Event Time$i", sharedPreferences.getString("Event Time${i + 1}", ""))
        }

        editor.putInt("event_count", eventCount - 1)
        editor.apply()
    }

    private fun editEvent(eventId: Int) {
        val eventName = sharedPreferences.getString("Event name$eventId", "")
        val eventDescription = sharedPreferences.getString("Event Decription$eventId", "")
        val eventDate = sharedPreferences.getString("Event Date$eventId", "")
        val eventTime = sharedPreferences.getString("Event Time$eventId", "")

        val editDialog = AlertDialog.Builder(this)
        editDialog.setTitle("Edit Event")

        val editView = layoutInflater.inflate(R.layout.edit_event_dialog, null)
        editDialog.setView(editView)

        val eventNameEditText = editView.findViewById<EditText>(R.id.event_name_edit_text)
        val eventDescriptionEditText = editView.findViewById<EditText>(R.id.event_description_edit_text)
        val eventDateEditText = editView.findViewById<EditText>(R.id.event_date_edit_text)
        val eventTimeEditText = editView.findViewById<EditText>(R.id.event_time_edit_text)

        eventNameEditText.setText(eventName)
        eventDescriptionEditText.setText(eventDescription)
        eventDateEditText.setText(eventDate)
        eventTimeEditText.setText(eventTime)

        editDialog.setPositiveButton("Save") { _, _ ->
            val editor = sharedPreferences.edit()
            editor.putString("Event name$eventId", eventNameEditText.text.toString())
            editor.putString("Event Decription$eventId", eventDescriptionEditText.text.toString())
            editor.putString("Event Date$eventId", eventDateEditText.text.toString())
            editor.putString("Event Time$eventId", eventTimeEditText.text.toString())
            editor.apply()

            taskArray[eventId] = "Event Name: ${eventNameEditText.text}\nEvent Description: ${eventDescriptionEditText.text}\nEvent Date: ${eventDateEditText.text}\nEvent Time: ${eventTimeEditText.text}"
            adapter.notifyDataSetChanged()
        }

        editDialog.setNegativeButton("Cancel") { _, _ ->
            // do nothing
        }

        editDialog.show()
    }
}