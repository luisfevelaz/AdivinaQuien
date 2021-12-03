package mx.lfvl.proyecto_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameActivity : AppCompatActivity() {
    private lateinit var recView: RecyclerView
    private lateinit var imgJugador: ImageView
    private lateinit var pregunta: String
    private lateinit var preguntaPersonaje: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val personaje = intent.getSerializableExtra("personaje") as Personaje

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


        val datos = arrayOf(
            Personaje(1,"Memo", R.drawable.c1),
            Personaje(2,"Laura", R.drawable.c2),
            Personaje(3,"Bryan", R.drawable.c3),
            Personaje(4,"Pablo", R.drawable.c4),
            Personaje(5,"Andrea", R.drawable.c5),
            Personaje(6,"John", R.drawable.c6),
            Personaje(7,"Omar", R.drawable.c7),
            Personaje(8,"Ramon", R.drawable.c8),
            Personaje(9,"Tony", R.drawable.c9),
            Personaje(10,"Maria", R.drawable.c10),
            Personaje(11,"Fernando", R.drawable.c11),
            Personaje(12,"Alejandra", R.drawable.c12),
            Personaje(13,"William", R.drawable.c13),
            Personaje(14,"Luisa", R.drawable.c14),
            Personaje(15,"Chuy", R.drawable.c15),
            Personaje(16,"Roman", R.drawable.c16),
            Personaje(17,"Ana", R.drawable.c17),
            Personaje(18,"Edgar", R.drawable.c18),
            Personaje(19,"Andres", R.drawable.c19),
            Personaje(20,"Pedro", R.drawable.c20),
            Personaje(21,"Sofia", R.drawable.c21),
            Personaje(22,"Manuel", R.drawable.c22),
            Personaje(23,"Carlos", R.drawable.c23),
            Personaje(24,"Jose", R.drawable.c24),
        )

        btnSend.setOnClickListener {
            btnNo.isEnabled = true
            btnSi.isEnabled = true
            btnSend.isEnabled = false
        }

        btnNo.setOnClickListener {
            btnNo.isEnabled = false
            btnSi.isEnabled = false
            btnSend.isEnabled = true
        }

        btnSi.setOnClickListener {
            btnNo.isEnabled = false
            btnSi.isEnabled = false
            btnSend.isEnabled = true
        }

        spPersonaje.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {

            }

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
        val adaptador = CartasItemsSeleccion(datos){
            Toast.makeText(this,"Pulsando a: ${it.nombre} ",Toast.LENGTH_SHORT).show()
        }

        recView.setHasFixedSize(true)
        recView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)


        recView.adapter = adaptador;
    }
}