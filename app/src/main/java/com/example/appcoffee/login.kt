package com.example.appcoffee
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.*
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class login : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener
    //Autenticacion con google para iniciar sesion
    private lateinit var cliente : GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnsesion : Button = findViewById(R.id.btnsesion)
        val txtUsuario : EditText = findViewById(R.id.txtusuario)
        val txtContrasena : EditText = findViewById(R.id.txtcontrasena)
        val btnuser_registrar : TextView = findViewById(R.id.btnregistrarse)
        val btn_olvidocontrasena : TextView = findViewById(R.id.btnolvidarcontrasena)
        //Definimos una variable para llamar al boton de google para logiarnos
        val btnLoginGoogle : ImageButton = findViewById(R.id.btngoogle)

        firebaseAuth = Firebase.auth
        //Autenticacion con google para iniciar sesion
        val opcion = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        //Autenticacion con google para iniciar sesion 
        cliente = GoogleSignIn.getClient(this,opcion)

        //Autenticacion con google para iniciar sesion
        btnLoginGoogle.setOnClickListener(){
            val intent = cliente.signInIntent
            startActivityForResult(intent,10001)

        }



        btnsesion.setOnClickListener(){
            singIn(txtUsuario.text.toString(), txtContrasena.text.toString())
        }

        btnuser_registrar.setOnClickListener(){
            val i = Intent(this, registrarse::class.java)
            startActivity(i)
        }

        btn_olvidocontrasena.setOnClickListener(){
            val b = Intent(this,olvidarContrasena::class.java)
            startActivity(b)
        }
    }
    //Autenticacion con google para iniciar sesion
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==10001){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val cuenta = task.getResult(ApiException::class.java)
            val credencial = GoogleAuthProvider.getCredential(cuenta.idToken,null)
            FirebaseAuth.getInstance().signInWithCredential(credencial)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val g = Intent(this,ConsumoServicios::class.java)
                        startActivity(g)
                    }
                    else{
                        Toast.makeText(baseContext,"No se pudo iniciar sesion con su cuenta de google",Toast.LENGTH_SHORT).show()

                    }

                }
        }
    }
    //Autenticacion con google para iniciar sesion
    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            val g = Intent(this,ConsumoServicios::class.java)
            startActivity(g)
        }
    }


    //funcion para iniciar sesion
    private fun singIn (email : String , password : String)
    {
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener (this){ task->
                if (task.isSuccessful){
                    val user = firebaseAuth.currentUser
                    //Este paso es para la verficacion del correo que se hizo en la pantalla registrarse
                    val verficacion = user?.isEmailVerified
                    if (verficacion == true){
                        Toast.makeText(baseContext,"Autenticación exitosa", Toast.LENGTH_SHORT).show()
                        //aqui se va a la pantalla principal
                        val i = Intent(this,ConsumoServicios::class.java)
                        startActivity(i)
                    }
                    else{
                        Toast.makeText(baseContext,"Verifique su correo", Toast.LENGTH_SHORT).show()
                    }

                }
                else {
                    Toast.makeText(baseContext,"Error de correo y/o contraseña",Toast.LENGTH_SHORT).show()
                }
            }
    }
}


