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

class registrarse : AppCompatActivity() {
    //conexión con firebase para crear una cuenta declaración
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        firebaseAuth = Firebase.auth

        //variables para llamar a los input de la interfaz de registrarse
        val txtNombreNuevo : EditText = findViewById(R.id.txtnombreusuario)
        val txtApellidoNuevo : EditText = findViewById(R.id.apellidousuario)
        val txtCorreoNuevo : EditText = findViewById(R.id.txtcorreoelectronico)
        val txtContrasena1 : EditText = findViewById(R.id.txtcontrasenanueva)
        val txtContrasena2 : EditText = findViewById(R.id.txtconfirmarcontrasenanueva)
        val btnCrearCuenta : Button = findViewById(R.id.btnregistrar)

        btnCrearCuenta.setOnClickListener(){
            //validaciones
            var pass1 = txtContrasena1.text.toString()
            var pass2 = txtContrasena2.text.toString()

            //hacemos comparación para confirmar si la dos contraseñas son iguales
            if(pass1.equals(pass2)){
                crearCuenta(txtCorreoNuevo.text.toString(),txtContrasena1.text.toString())

            }
            else{
                Toast.makeText(baseContext, "Error, las contraseñas no son iguales",Toast.LENGTH_SHORT).show()
                txtContrasena1.requestFocus()
            }
        }



        val btn_llevarAinicio : TextView = findViewById(R.id.btninicio_sesion)

        btn_llevarAinicio.setOnClickListener(){
            val x = Intent (this,login::class.java)
            startActivity(x)
        }
    }

    private fun crearCuenta(correo:String, password1:String){
        firebaseAuth.createUserWithEmailAndPassword(correo,password1).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                //Aca llamo a la funcion verificaciónEmail
                verficacionEmail()
                Toast.makeText(baseContext,"Se ha registrado correctamente, se requiere verficacion de correo",Toast.LENGTH_SHORT).show()

            }
            else{
                Toast.makeText(baseContext,"No se pudo completar el registro, algo salio mal" +task.exception, Toast.LENGTH_SHORT).show()

            }

        }
    }
    //Función para enviar verficación de email
    private fun verficacionEmail(){
        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                Toast.makeText(baseContext, "Verificación de correo enviada", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(baseContext,"Error en la verficación de correo", Toast.LENGTH_SHORT).show()
            }
        }
    }
}