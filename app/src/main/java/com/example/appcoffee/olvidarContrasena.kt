package com.example.appcoffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class olvidarContrasena : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_olvidar_contrasena)

        //declaro variables para restablecer la contraseña
        val txtemail : EditText = findViewById(R.id.txtcorreocambiarcontrasena)
        val btnCambiarContra : Button = findViewById(R.id.btnrestablecercontrasena)

        //llamo a la variable btnCambiarContra

        btnCambiarContra.setOnClickListener(){
            cambiarContrasena(txtemail.text.toString())

        }


        firebaseAuth = Firebase.auth

        //declaro variables para pasar a otras pantallas
        val volver_sesion : TextView = findViewById(R.id.btnvolverasesion)
        val volver_registrarse : TextView = findViewById(R.id.btnvolveraregistrarse)


        //este paso es opara que me lleve a la apntalla de inicio de sesion
        volver_sesion.setOnClickListener(){
            val a = Intent(this,login::class.java)
            startActivity(a)
        }

        //este paso es opara que me lleve a la pantalla de registro
        volver_registrarse.setOnClickListener(){
            val r = Intent(this,registrarse::class.java)
            startActivity(r)
        }
    }
    //Funcion para cambiar de contraseña
    private fun cambiarContrasena(email:String){
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(){ task ->
                if(task.isSuccessful){
                    Toast.makeText(baseContext,"Se ha enviado un link al correo ingresado", Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(baseContext,"Ocurrio un error con el restablecimiento de contraseña",Toast.LENGTH_SHORT).show()

                }


            }
    }
}