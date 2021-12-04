package mx.lfvl.proyecto_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class GameActivity1 : AppCompatActivity() {
    private lateinit var recView: RecyclerView
    private lateinit var imgJugador: ImageView
    private var pregunta: Int = 0;
    private var tipoPregunta: Int = 0;
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
        //Generar el personaje rival aleatoriamente
        val random = Random(System.nanoTime()).nextInt(24 - 1 + 1) + 1;
        val rival = datos[random-1];

        btnSend.setOnClickListener {
            if(tipoPregunta == 0){
                revisarArray(pregunta,rival)
            }else{
                if(rival.nombre == preguntaPersonaje){
                    alertResultado("Felicidades, ganaste!!!");
                }else{
                    alertResultado("Perdiste, el personaje era: " + rival.nombre);
                }
                btnSend.isEnabled = false;
                //finish();
            }

            println("Rival es: " + rival.nombre)
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
                tipoPregunta = 1;
                if (parent != null) {
                    preguntaPersonaje = ""+parent.getItemAtPosition(position)
                }

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
                pregunta = position;
                tipoPregunta = 0;
            }
        }

        val adaptador = CartasItemsSeleccion(datos,datos){
            Toast.makeText(this,"Pulsando a: ${it.nombre} ",Toast.LENGTH_SHORT).show()
        }

        recView.setHasFixedSize(true)
        recView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recView.adapter = adaptador;
    }

    fun alertResultado(resultado: String){
        val dialog = AlertDialog.Builder(this)
            .setTitle("Resultado del juego: ")
            .setMessage(resultado)
            .setPositiveButton("Ok"){
                    dialog, int ->
            }.show()
    }


    fun revisarArray(pregunta: Int, rival: Personaje){
        var respuesta = false;
        when(pregunta){
            0->respuesta = rival.id in ArraysCaracteristicos.ArrayHombre
            1->respuesta = !(rival.id in ArraysCaracteristicos.ArrayHombre)
            2->respuesta = rival.id in ArraysCaracteristicos.TezBlancaClara
            3->respuesta = rival.id in ArraysCaracteristicos.TezBlanca
            4->respuesta = rival.id in ArraysCaracteristicos.TezMorena
            5->respuesta = rival.id in ArraysCaracteristicos.Lentes
            6->respuesta = rival.id in ArraysCaracteristicos.Mascara
            7->respuesta = rival.id in ArraysCaracteristicos.PeloNegro
            8->respuesta = rival.id in ArraysCaracteristicos.PeloCafe
            9->respuesta = rival.id in ArraysCaracteristicos.PeloRubio
            10->respuesta = rival.id in ArraysCaracteristicos.PeloGris
            11->respuesta = rival.id in ArraysCaracteristicos.PeloCorto
            12->respuesta = rival.id in ArraysCaracteristicos.PeloLargo
            13->respuesta = !(rival.id in ArraysCaracteristicos.Pelon)
            14->respuesta = rival.id in ArraysCaracteristicos.Bigote
            15->respuesta = rival.id in ArraysCaracteristicos.Barba
        }

        alertRespuesta(respuesta)

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
            }.setCancelable(false)
            .show()
    }

}