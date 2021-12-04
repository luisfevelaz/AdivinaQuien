package mx.lfvl.proyecto_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.stream.DoubleStream.builder

class GameActivity : AppCompatActivity() {
    private lateinit var recView: RecyclerView
    private lateinit var imgJugador: ImageView
    private lateinit var pregunta: String
    private lateinit var preguntaPersonaje: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val personaje = intent.getSerializableExtra("personaje") as Personaje
        val username = intent.getStringExtra("username")

        recView = findViewById(R.id.recView)
        imgJugador = findViewById(R.id.imgJugador)
        imgJugador.setImageResource(personaje.imagen)

        val btnSend: Button = findViewById(R.id.btnSend)
        val btnSi: Button = findViewById(R.id.btnSi)
        val btnNo: Button = findViewById(R.id.btnNo)

        val spPreguntas: Spinner = findViewById(R.id.spPreg)
        val spPersonaje: Spinner = findViewById(R.id.spPersonaje)

        val adaptadorPreg = ArrayAdapter.createFromResource(this,R.array.preguntas_array,android.R.layout.simple_spinner_item)
        val adaptadorPersonaje = ArrayAdapter.createFromResource(this,R.array.nombres_array,android.R.layout.simple_spinner_item)

        adaptadorPreg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPreguntas.adapter = adaptadorPreg

        adaptadorPersonaje.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPersonaje.adapter = adaptadorPersonaje

        btnSi.isEnabled = false
        btnNo.isEnabled = false


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
            btnNo.isEnabled = true
            btnSi.isEnabled = true
            btnSend.isEnabled = false
            alertPregunta("¿Tu personaje es mujer?");
        }

        btnNo.setOnClickListener {
            btnNo.isEnabled = false
            btnSi.isEnabled = false
            btnSend.isEnabled = true
            //alertResultado("Ganaste");
        }

        btnSi.setOnClickListener {
            btnNo.isEnabled = false
            btnSi.isEnabled = false
            btnSend.isEnabled = true
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
                val preg: String =pos.toString()
                preguntaPersonaje = preg
                Toast.makeText(
                    applicationContext,
                    "Opcion: $preg",
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
                val preg: String =pos.toString()
                preguntaPersonaje = preg
                Toast.makeText(
                    applicationContext,
                    "Opcion: $preg",
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
                .setPositiveButton("Sí"){
                    dialog,int -> enviarRespuesta(true)
                }
                .setNegativeButton("No"){
                    dialog,int-> enviarRespuesta(false)
                }
                .setCancelable(false)
                .show()
    }

    fun alertRespuesta(resp: String){
        val dialog = AlertDialog.Builder(this)
                .setTitle("Tu oponente responde: ")
                .setMessage(resp)
                .setPositiveButton("Ok"){
                    dialog, int ->
                }.show()
    }

    fun alertResultado(resultado: String){
        val dialog = AlertDialog.Builder(this)
                .setTitle("Resultado del juego: ")
                .setMessage(resultado)
                .setPositiveButton("Ok"){
                    dialog, int ->
                }
                .setCancelable(false)
                .show()
    }
    fun enviarRespuesta(resp: Boolean){
        println("Respuesta: "+ resp)
    }
}