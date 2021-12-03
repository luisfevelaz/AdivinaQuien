package mx.lfvl.proyecto_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@IgnoreExtraProperties
data class Usuario(val username: String? = null, val password: String? = null, val online: Boolean? = false) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "username" to username,
            "password" to password,
            "online" to online
        )
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var btnInicio: Button
    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var newUser: CheckBox
    private lateinit var db: DatabaseReference
    private lateinit var userInSession: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnInicio = findViewById(R.id.inicio)
        txtUser = findViewById(R.id.txtUser)
        txtPassword = findViewById(R.id.txtPassword)
        newUser = findViewById(R.id.checkNew)

        db = Firebase.database.reference

        btnInicio.setOnClickListener {
            var username = txtUser.text.toString()
            if(newUser.isChecked && txtUser.text.toString().isNotEmpty() && txtPassword.text.toString().isNotEmpty()) {

                db.child("Usuarios").get().addOnSuccessListener {
                    var existe = false
                    for(element: DataSnapshot in it.getChildren()){
                        var usernameReg = element.value as HashMap<String, Object>
                        Log.i("Base de datos", "Usuario en registro: ${usernameReg.get("username")}")
                        if(usernameReg.get("username").toString()==username){
                            existe = true
                        }
                    }

                    if(!existe){
                        var usuario = Usuario(txtUser.text.toString(), txtPassword.text.toString(), false).toMap()
                        db.child("Usuarios").push().setValue(usuario)
                    }
                }.addOnFailureListener{
                    Log.e("Base de datos", "Error, no existe el objeto Usuarios", it)
                }
            }else if(txtUser.text.toString().isNotEmpty() && txtPassword.text.toString().isNotEmpty()){
                db.child("Usuarios").get().addOnSuccessListener {
                    var existe = false
                    var userKey = ""
                    var password = txtPassword.text.toString()
                    for(element: DataSnapshot in it.getChildren()){
                        var usernameReg = element.value as HashMap<String, Object>
                        Log.i("Base de datos", "Usuario en registro: ${usernameReg.get("username")}")
                        if(usernameReg.get("username").toString()==username && usernameReg.get("password").toString()==password){
                            existe = true
                            userKey = element.key as String
                            password = usernameReg.get("password").toString()
                        }
                    }

                    if(existe){
                        //ACTUALIZAR PROPIEDAD online DEL USUARIO
                        var usuarioUpdate = Usuario(username, password, true).toMap()
                        var update = mapOf(
                            "/${userKey}" to usuarioUpdate
                        )
                        db.child("Usuarios").updateChildren(update)

                        //Variable para conocer el usuario en sesión
                        userInSession = username

                        val intent = Intent(this,UsuarioSesion::class.java)
                        startActivity(intent)
                    }
                }.addOnFailureListener{
                    Log.e("Base de datos", "Error, no existe el objeto Usuarios", it)
                }

                /*val intent = Intent(this,UsuarioSesion::class.java)
                startActivity(intent)*/
            }
        }

    }

    /*fun usuarioExistente(username: String){
        db.child("Usuarios").get().addOnSuccessListener {
            for(element: DataSnapshot in it.getChildren()){
                var usernameReg = element.value as HashMap<String, Object>
                Log.i("Base de datos", "Usuario en registro: ${usernameReg.get("username")}")
                if(usernameReg.get("username").toString()==username){
                    existe = true
                }
            }
        }.addOnFailureListener {
            Log.e("Base de datos", "Error, no existe el objeto Usuarios", it)
        }
    }*/
}