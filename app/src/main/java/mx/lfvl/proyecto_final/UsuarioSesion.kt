package mx.lfvl.proyecto_final

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dmax.dialog.SpotsDialog
import java.lang.Exception
import kotlin.random.Random

@IgnoreExtraProperties
data class Partida(val status: String? = null, val player1: String? = null,
                   val player2: String? = null, val message: String? = null,
                   val response: String? = null, val turn: String? = null) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "status" to status,
            "player1" to player1,
            "player2" to player2,
            "message" to message,
            "response" to response,
            "turn" to turn,
        )
    }
}

class UsuarioSesion : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private var partidaInSession: String = ""
    private var oponent: String = ""
    private var partidaHost: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_sesion)
        val personajeImagen: ImageView = findViewById(R.id.personaje);
        val btnOnline: Button = findViewById(R.id.btnOnline);
        val btnIndividual: Button = findViewById(R.id.btnIndividual);
        db = Firebase.database.reference

        val items = listOf<Personaje>(
            Personaje(1,"Memo", R.drawable.c1,false),
            Personaje(2,"Laura", R.drawable.c2, false),
            Personaje(3,"Bryan", R.drawable.c3, false),
            Personaje(4,"Pablo", R.drawable.c4, false),
            Personaje(5,"Andrea", R.drawable.c5, false),
            Personaje(6,"John", R.drawable.c6, false),
            Personaje(7,"Omar", R.drawable.c7, false),
            Personaje(8,"Ramon", R.drawable.c8, false),
            Personaje(9,"Tony", R.drawable.c9, false),
            Personaje(10,"Maria", R.drawable.c10, false),
            Personaje(11,"Fernando", R.drawable.c11, false),
            Personaje(12,"Alejandra", R.drawable.c12, false),
            Personaje(13,"William", R.drawable.c13, false),
            Personaje(14,"Luisa", R.drawable.c14, false),
            Personaje(15,"Chuy", R.drawable.c15, false),
            Personaje(16,"Roman", R.drawable.c16, false),
            Personaje(17,"Ana", R.drawable.c17, false),
            Personaje(18,"Edgar", R.drawable.c18, false),
            Personaje(19,"Andres", R.drawable.c19, false),
            Personaje(20,"Pedro", R.drawable.c20, false),
            Personaje(21,"Sofia", R.drawable.c21, false),
            Personaje(22,"Manuel", R.drawable.c22, false),
            Personaje(23,"Carlos", R.drawable.c23, false),
            Personaje(24,"Jose", R.drawable.c24, false),
        );

        val random = Random(System.nanoTime()).nextInt(24 - 1 + 1) + 1;
        personajeImagen.setImageResource(items[random-1].imagen); // se obtiene aleatoriamente el personaje

        val personaje = items[random-1]; // se almacena el personaje en una variable para enviárselo a una de las dos opciones de juego

        btnOnline.setOnClickListener {
            val username = intent.getStringExtra("username")
            var partidaExistente = false

            val intent = Intent(this,GameActivity::class.java)
            intent.putExtra("personaje",personaje)
            intent.putExtra("username",username)

            val loader = LoaderDialog(this)
            loader.showLoader()

            Handler().apply {
                val runnable = object : Runnable {
                    override fun run() {
                        Log.i("Handler", "Ejecutado nuevamente")
                        if(loader.loaderActive()){
                            if(partidaHost == "" && loader.loaderActive()) {
                                db.child("Partidas").get().addOnSuccessListener {
                                    for (element: DataSnapshot in it.getChildren()) {
                                        var partidaReg = element.value as HashMap<String, Object>
                                        Log.i("Base de datos", "Partida en registro: ${partidaReg.get("status")}")
                                        if (partidaReg.get("status").toString() == "waiting") {
                                            partidaExistente = true
                                            partidaInSession = element.key as String
                                            oponent = partidaReg.get("player1").toString()
                                            break
                                        }
                                    }

                                    if (partidaExistente) {
                                        //Colocamos al usuario actual como el Player2 pues él está entrando
                                        var partidaUpdate = Partida("full",
                                            oponent,
                                            username,
                                            "",
                                            "",
                                            "player1").toMap()
                                        var update = mapOf(
                                            "/${partidaInSession}" to partidaUpdate
                                        )
                                        db.child("Partidas").updateChildren(update)
                                        loader.dismissLoader()

                                        intent.putExtra("partidaInSession",partidaInSession)
                                        intent.putExtra("type", "player2")
                                        intent.putExtra("oponent", oponent)
                                        startActivity(intent)
                                    }
                                }.addOnFailureListener {
                                    Log.e("Base de datos", "Error, no existe el objeto Partidas", it)
                                }
                            }

                            if(!partidaExistente && partidaHost==""){
                                db.child("Partidas").get().addOnSuccessListener {
                                    for (element: DataSnapshot in it.getChildren()) {
                                        var partidaReg = element.value as HashMap<String, Object>
                                        Log.i("Base de datos", "Partida en registro: ${partidaReg.get("status")}")
                                        if (partidaReg.get("status").toString() == "full") {
                                            if(partidaReg.get("player1").toString()==username || partidaReg.get("player2").toString()==username){
                                                partidaExistente = true
                                                break
                                            }
                                        }
                                    }

                                    if(!partidaExistente){
                                        var partida = Partida("waiting",
                                            username,
                                            "",
                                            "",
                                            "",
                                            "player1").toMap()
                                        partidaHost = db.child("Partidas").push().key as String
                                        var update = mapOf(
                                            "/${partidaHost}" to partida
                                        )
                                        db.child("Partidas").updateChildren(update)
                                    }
                                }.addOnFailureListener{
                                    Log.e("Base de datos", "Error, no existe el objeto Partidas", it)
                                }
                            }

                            if(!partidaExistente && partidaHost != "" && loader.loaderActive()){
                                //Si la partida ya tiene a ambos jugadores la iniciamos
                                db.child("Partidas").get().addOnSuccessListener {
                                    var partidaComplete = false
                                    for (element: DataSnapshot in it.getChildren()) {
                                        var partidaReg = element.value as HashMap<String, Object>
                                        if (partidaReg.get("status").toString() == "full" && partidaReg.get("player1").toString()==username) {
                                            Log.i("Base de datos", "Número de jugadores completo")
                                            oponent = partidaReg.get("player2").toString()
                                            partidaComplete = true
                                            break
                                        }
                                    }

                                    if (partidaComplete) {
                                        loader.dismissLoader()
                                        intent.putExtra("partidaInSession",partidaHost)
                                        intent.putExtra("type", "player1")
                                        intent.putExtra("oponent", oponent)
                                        startActivity(intent)
                                    }
                                }.addOnFailureListener {
                                    Log.e("Base de datos", "Error, no existe el objeto Partidas", it)
                                }
                            }

                            postDelayed(this, 5000)
                        }
                    }
                }
                postDelayed(runnable, 1000)
            }
        }

        btnIndividual.setOnClickListener {
            val username = intent.getStringExtra("username")
            val intent = Intent(this,GameActivity1::class.java)
            intent.putExtra("personaje",personaje)
            intent.putExtra("username",username)
            startActivity(intent)
        }
    }
}