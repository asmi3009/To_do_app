package com.example.task_to_do

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Bundle
//import android.provider.CalendarContract.Calendars
import android.widget.Button
//import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.text.*

class Addtask : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var eventdate: EditText
    private lateinit var eventtime : EditText
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addtask)
        sharedPreferences = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        val eventname = findViewById<EditText>(R.id.edevename)
        val eventdecription = findViewById<EditText>(R.id.etevendec)
        eventdate = findViewById(R.id.et1)
         eventtime = findViewById(R.id.eteventdate)
        val btnsub1 = findViewById<Button>(R.id.Sub1)
        val btnback = findViewById<Button>(R.id.back1)
        eventdate.setOnClickListener {
            showdatepicker()
        }
        eventtime.setOnClickListener {
            showtimepicker()
        }
        btnback.setOnClickListener {
            intent = Intent(applicationContext, first_screen::class.java)
            startActivity(intent)
        }
        btnsub1.setOnClickListener {
            val etevename = eventname.text.toString()
            val eventdecrip = eventdecription.text.toString()
            val etdate = eventdate.text.toString()
            val ettime = eventtime.text.toString()
            if (etevename.isEmpty() && eventdecrip.isEmpty() && etdate.isEmpty() && ettime.isEmpty()) {
                Toast.makeText(this, "Fillup the details", Toast.LENGTH_SHORT).show()
            } else {
                // store date in sharedpref
                val editor = sharedPreferences.edit()
                val etCount = sharedPreferences.getInt("event_count", 0)
                editor.putString("Event name$etCount", etevename)
                editor.putString("Event Decription$etCount", eventdecrip)
                editor.putString("Event Date$etCount", etdate)
                editor.putString("Event Time$etCount", ettime)
                editor.putInt("event_count", etCount + 1)
                editor.apply()

                // increment event count
                val eventCount = sharedPreferences.getInt("event_count", 0) + 1
                editor.putInt("event_count", eventCount)
                editor.apply()

                //navigate to other side
                intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)


            }
        }

    }

    private fun showtimepicker() {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val amPm = if (hourOfDay < 12) "AM" else "PM"
                val selectedTime = String.format("%02d:%02d %s", if (hourOfDay == 0) 12 else if (hourOfDay > 12) hourOfDay - 12 else hourOfDay, minute, amPm)
                eventtime.setText(selectedTime)
            },
            hour,
            minute,
            true // 12-hour format with AM/PM
        )
        timePicker.show()
    }

    private fun showdatepicker() {
        val datePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                eventdate.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()

    }
}
