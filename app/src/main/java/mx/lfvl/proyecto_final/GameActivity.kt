package mx.lfvl.proyecto_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.lfvl.proyecto_final.Partida

class GameActivity : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var recView: RecyclerView
    private lateinit var imgJugador: ImageView
    private lateinit var  btnSend: Button
    private lateinit var  btnSi: Button
    private lateinit var  btnNo: Button
    private lateinit var pregunta: String
    private lateinit var preguntaPersonaje: String
    private var username: String = ""
    private var oponent: String = ""
    private var partidaInSession: String = ""
    private var type: String = ""  //player1 o player2 dependiendo de la conexión
    private var turn: String = "player1"
    private var turnType: String = "ask"
    private var preguntaFinal: Boolean = false
    private lateinit var nombreUser: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        db = Firebase.database.reference

        val personaje = intent.getSerializableExtra("personaje") as Personaje
        username = intent.getStringExtra("username") as String
        oponent = intent.getStringExtra("oponent") as String
        partidaInSession = intent.getStringExtra("partidaInSession") as String
        type = intent.getStringExtra("type") as String

        recView = findViewById(R.id.recView)
        imgJugador = findViewById(R.id.imgJugador)
        imgJugador.setImageResource(personaje.imagen)
        nombreUser = findViewById(R.id.resp)
        nombreUser.text = nombreUser.text.toString()+username

        btnSend = findViewById(R.id.btnSend)
        btnSi = findViewById(R.id.btnSi)
        btnNo = findViewById(R.id.btnNo)

        val spPreguntas: Spinner = findViewById(R.id.spPreg)
        val spPersonaje: Spinner = findViewById(R.id.spPersonaje)

        val adaptadorPreg = ArrayAdapter.createFromResource(this,R.array.preguntas_array,android.R.layout.simple_spinner_item)
        val adaptadorPersonaje = ArrayAdapter.createFromResource(this,R.array.nombres_array,android.R.layout.simple_spinner_item)

        adaptadorPreg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPreguntas.adapter = adaptadorPreg

        adaptadorPersonaje.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPersonaje.adapter = adaptadorPersonaje

        if(type==turn){
            deshabilitar()
            habilitarPregunta()
        }
        else{
            deshabilitar()
            turnType = "answer"
            esperarTurno(turnType)
        }

        var datos = arrayOf(
            Personaje(1,"Memo", R.drawable.c1, false),
            Personaje(2,"Laura", R.drawable.c2, false),
            Personaje(3,"Bryan", R.drawable.c3, false),
            Personaje(4,"Pablo", R.drawable.c4, false),
            Personaje(5,"Andrea", R.drawable.c5, false),
            Personaje(6,"John", R.drawable.c6,false),
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
        )
        datos.shuffle(); // esta función ordena aleatoriamente los elementos del arreglo.

        btnSend.setOnClickListener {
            if(preguntaPersonaje != ""){
                deshabilitar()
                turnType = "ask"
                enviarPregunta(preguntaPersonaje)
            }
        }

        btnNo.setOnClickListener {
            deshabilitar()
            turnType = "answer"
            enviarRespuesta(false)
        }

        btnSi.setOnClickListener {
            deshabilitar()
            turnType = "answer"
            enviarRespuesta(true)
        }

        spPersonaje.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //asigna valores a la variable preguntaPersonaje
                val pos = parent?.getItemAtPosition(position)
                val preg: String = pos.toString()
                preguntaFinal = true
                preguntaPersonaje = preg
                Toast.makeText(
                    applicationContext,
                    "Opción: $preg",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        spPreguntas.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //asigna valores a la variable pregunta
                val pos = parent?.getItemAtPosition(position)
                val preg: String = pos.toString()
                preguntaFinal = false
                preguntaPersonaje = preg
                Toast.makeText(
                    applicationContext,
                    "Opción: $preg",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //val adaptador = CartasItemsSeleccion(datos)
        val adaptador = CartasItemsSeleccion(datos,datos){
            Toast.makeText(this,"Pulsando a: ${it.nombre} ",Toast.LENGTH_SHORT).show()
        }

        recView.setHasFixedSize(true)
        recView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recView.adapter = adaptador;
    }

    fun alertPregunta(pregunta: String){
        val dialog = AlertDialog.Builder(this)
                .setTitle("Tu oponente pregunta: ")
                .setMessage(pregunta)
                .setPositiveButton("Ok"){
                    dialog,int ->
                        habilitarRespuesta()
                }
                .setCancelable(false)
                .show()
    }

    fun alertRespuesta(resp: Boolean){
        var msg = "Sí"
        if(resp == false){
            msg = "No";
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Tu oponente responde: ")
            .setMessage(msg)
            .setPositiveButton("Ok"){
                dialog, int ->
                    //CHECAR SI ERA LA PREGUNTA DE PERSONAJE Y SI ES UN SÍ O UN NO
                    if(preguntaFinal){
                        if(msg=="Sí"){
                            alertResultado("Felicidades, ganaste!!!");
                        }
                        else{
                            alertResultado("¡Ups perdiste!");
                        }
                    }
                    else{
                        //Si no es la pregunta final entonces habilitamos más preguntas
                        habilitarPregunta()
                    }

            }
            .setCancelable(false)
            .show()
    }

    fun alertResultado(resultado: String){
        val dialog = AlertDialog.Builder(this)
            .setTitle("Resultado del juego: ")
            .setMessage(resultado)
            .setPositiveButton("Ok"){
                dialog, int ->
                    //VER SI SE PUEDE SALIR DE LA ACTIVIDAD
                    //REGISTRAR RESULTADO EN PERDIDAS JUGADAS GANADAS DEL USUARIO
            }
            .setCancelable(false)
            .show()
    }

    fun enviarPregunta(pregunta: String){
        var newTurn = "player2"
        var player1 = username
        var player2 = oponent
        if(type!="player1"){
            newTurn = "player1"
            player1 = oponent
            player2 = username
        }

        var partidaUpdate = Partida("full",
            player1,
            player2,
            pregunta,
            "",
            newTurn).toMap()
        var update = mapOf(
            "/${partidaInSession}" to partidaUpdate
        )
        db.child("Partidas").updateChildren(update)
        esperarTurno(turnType)
    }

    fun enviarRespuesta(resp: Boolean){
        //var received = false;
        var newTurn = "player2"
        var player1 = username
        var player2 = oponent
        if(type!="player1"){
            newTurn = "player1"
            player1 = oponent
            player2 = username
        }
        var msg = "Sí"
        if(resp == false)
            msg = "No";

        /*Handler().apply {
            val runnable = object : Runnable {
                override fun run() {*/
                    var partidaUpdate = Partida("full",
                        player1,
                        player2,
                        "",
                        msg,
                        newTurn).toMap()
                    var update = mapOf(
                        "/${partidaInSession}" to partidaUpdate
                    )
                    db.child("Partidas").updateChildren(update)

        /*            if(!received)
                        postDelayed(this, 7000)
                }
            }
            postDelayed(runnable, 500)
        }*/
        println("Respuesta: "+ resp)
        esperarTurno(turnType)
    }

    fun esperarTurno(tipo: String){
        var received = false
        var text = ""
        Handler().apply {
            val runnable = object : Runnable {
                override fun run() {
                    if(!received) {
                        var msj = ""
                        var res = ""
                        db.child("Partidas").get().addOnSuccessListener {
                            for (element: DataSnapshot in it.getChildren()) {
                                var partidaReg = element.value as HashMap<String, Object>
                                Log.i("Turno",partidaReg.get("turn").toString())
                                if(partidaReg.get("turn").toString() == type) {
                                    received = true
                                    msj = partidaReg.get("message").toString()
                                    res = partidaReg.get("response").toString()
                                }
                            }

                            if (received) {
                                text = msj
                                if(tipo == "ask")
                                    text = res
                            }
                        }.addOnFailureListener {
                            Log.e("Base de datos", "Error, no existe el objeto Partidas", it)
                        }

                        postDelayed(this, 3000)
                    }
                    else{
                        if(tipo=="ask"){
                            if(text=="Sí") {
                                alertRespuesta(true)
                            }
                            else {
                                alertRespuesta(false)
                            }
                        }
                        else{
                            alertPregunta(text)
                        }
                    }
                }
            }
            postDelayed(runnable, 500)
        }
    }

    fun habilitarPregunta(){
        btnSend.isEnabled = true
    }

    fun habilitarRespuesta(){
        btnNo.isEnabled = true
        btnSi.isEnabled = true
    }

    fun deshabilitar(){
        btnNo.isEnabled = false
        btnSi.isEnabled = false
        btnSend.isEnabled = false
    }
}