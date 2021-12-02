package mx.lfvl.proyecto_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var btnInicio: Button
    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var newUser: CheckBox
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnInicio = findViewById(R.id.inicio)
        txtUser = findViewById(R.id.txtUser)
        txtPassword = findViewById(R.id.txtPassword)
        newUser = findViewById(R.id.checkNew)

        db = Firebase.database.reference

        btnInicio.setOnClickListener {
            if(newUser.isChecked && txtUser.text.toString().isNotEmpty() && txtPassword.text.toString().isNotEmpty()) {
                //println("Enviando a Base de datos: " + txtUser.text.toString() + " " + txtPassword.text.toString())
                //val usuarioMap = HashMap<String, String>()
                //usuarioMap.put("username", txtUser.text.toString())

                val usuario = mapOf(
                    "username" to txtUser.text.toString(),
                    "password" to txtPassword.text.toString()
                )
                db.child("Usuarios").setValue(usuario)

                val intent = Intent(this,UsuarioSesion::class.java)
                startActivity(intent)
            }else if(txtUser.text.toString().isNotEmpty()){
                //println("Usuario: "+txtUser.text.toString());
                //println("contraseña: "+txtPassword.text.toString());

                val intent = Intent(this,UsuarioSesion::class.java)

                startActivity(intent)
            }
        }

    }
}