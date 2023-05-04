package com.example.appcoffee

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        startTimer()

        // Este paso es para validadcion  de internet

        val textoConexion = findViewById<TextView>(R.id.mensaje)
        Thread(Runnable {
            while (true){
                var textoInicial = "No tienes conexión"

                val servConec = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

                val infoRed = servConec.allNetworkInfo

                for(ir in infoRed) {
                    // Aca verificamos si esta conectado a wifi

                    if (ir.typeName.equals("WIFI", ignoreCase = true))
                        if (ir.isConnected) textoInicial = "Conectado con WIFI"


                    // Aca verificamos si esta conectado a datos moviles
                    if (ir.typeName.equals("MOVILE", ignoreCase = true))
                        if (ir.isConnected) textoInicial = "Conectado con datos moviles"


                    }
                runOnUiThread {
                    textoConexion.text = textoInicial
                }

            }
        }).start()
    }
    fun loadData(inFile: String): String {
        var tContents = ""

        try {
            val stream = assets.open(inFile)

            val size = stream.available()
            val buffer =  ByteArray(size)
            stream.read(buffer)
            stream.close()
            tContents = String(buffer)
        } catch (e: IOException) {
            // manejar excepciones aqui
        }

        return tContents
    }
    fun startTimer(){
        object:CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
               val intent= Intent(applicationContext,login::class.java).apply{}
                startActivity(intent)
            }

        }.start()

    }

}