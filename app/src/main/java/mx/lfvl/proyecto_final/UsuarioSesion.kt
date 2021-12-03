package mx.lfvl.proyecto_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_usuario_sesion.*
import kotlin.random.Random

class UsuarioSesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_sesion)
        val personajeImagen: ImageView = findViewById(R.id.personaje);
        val btnOnline: Button = findViewById(R.id.btnOnline);
        val btnIndividual: Button = findViewById(R.id.btnIndividual);


        val items = listOf<Personaje>(
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
        );

        val random = Random(System.nanoTime()).nextInt(24 - 1 + 1) + 1;
        personajeImagen.setImageResource(items[random-1].imagen); // se obtienen aleatoriamente el personaje

        val personaje = items[random-1]; // se almacena el personaje en una variable para enviarselo a una de las dos opciones de juego

        btnOnline.setOnClickListener {
            val intent = Intent(this,GameActivity::class.java)
            intent.putExtra("personaje",personaje)
            startActivity(intent)

        }

        btnIndividual.setOnClickListener {
            val intent = Intent(this,GameActivity::class.java)
            intent.putExtra("personaje",personaje)
            startActivity(intent)
        }



    }
}