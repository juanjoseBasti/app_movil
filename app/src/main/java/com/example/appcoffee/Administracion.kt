package com.example.appcoffee

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class Administracion : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administracion)

        val options = listOf("Seleccione", "Gastos", "Perdidas", "Ganancias", "Inversiones")
        val mySpinner = findViewById<Spinner>(R.id.mySpinner)

        mySpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedOption = options[position]
                when (selectedOption) {
                    "Gastos" -> {
                        val intent = Intent(this@Administracion, Gastos::class.java)
                        startActivity(intent)
                    }
                    "Perdidas" -> {
                        val intent = Intent(this@Administracion, Perdidas::class.java)
                        startActivity(intent)
                    }
                    "Ganancias" -> {
                        val intent = Intent(this@Administracion, Ganancias::class.java)
                        startActivity(intent)
                    }
                    "Inversiones" -> {
                        val intent = Intent(this@Administracion, Inversiones::class.java)
                        startActivity(intent)
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Lógica para manejar cuando no se selecciona ninguna opción
            }
        })

    }
}
