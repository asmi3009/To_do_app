package com.example.task_to_do

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etdate: EditText
    private val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
       sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
         val etdate = findViewById<EditText>(R.id.etdate)
        val etname = findViewById<EditText>(R.id.etname)
        val etadd = findViewById<EditText>(R.id.etadd)
        val etphone = findViewById<EditText>(R.id.etphone)
        val btnrej = findViewById<Button>(R.id.rejbut)

        // Check if user has already registered
        if (sharedPreferences.contains("user_name")) {
            // User has already registered, navigate to first screen
            val intent = Intent(this, first_screen::class.java)
            startActivity(intent)
            finish() // Close this activity
            return
        }


        btnrej.setOnClickListener{
            val edtname = etname.text.toString()
            val edtdate = etdate.text.toString()
            val edtadd = etadd.text.toString()
            val edtphone = etphone.text.toString()

            if (edtname.isEmpty() || edtdate.isEmpty() || edtadd.isEmpty() || edtphone.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Store data in SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("user_name", edtname)
            editor.putString("user_date", edtdate)
            editor.putString("user_address", edtadd)
            editor.putString("user_phone_number", edtphone)
            editor.apply() // or editor.commit() for older API levels

            // Navigate to the next activity
            val intent = Intent(this, first_screen::class.java)
            startActivity(intent)
            Toast.makeText(this, "registered Sucessfull", Toast.LENGTH_SHORT).show()
        }

    }




}