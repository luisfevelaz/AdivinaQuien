package mx.lfvl.proyecto_final

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import dmax.dialog.SpotsDialog
import kotlin.random.Random

class UsuarioSesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_sesion)
        val personajeImagen: ImageView = findViewById(R.id.personaje);
        val btnOnline: Button = findViewById(R.id.btnOnline);
        val btnIndividual: Button = findViewById(R.id.btnIndividual);

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
        personajeImagen.setImageResource(items[random-1].imagen); // se obtienen aleatoriamente el personaje

        val personaje = items[random-1]; // se almacena el personaje en una variable para enviarselo a una de las dos opciones de juego

        btnOnline.setOnClickListener {
            val username = intent.getStringExtra("username")

            /*val dialog = AlertDialog.Builder(this)
                .setTitle("Juego online")
                .setMessage("Estamos buscando a tu oponente")
                .setNegativeButton("No"){
                    dialog, int ->

                }
                .setCancelable(false)
                .show()*/

            /*val dialog: AlertDialog = SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Estamos buscando a tu oponente")
                /*.setCancelListener(DialogInterface.OnCancelListener {
                    Handler().apply {
                        val runnable = object : Runnable {
                            override fun run() {
                                Log.i("Handler", "Ejecutado nuevamente")
                                postDelayed(this, 3000)
                            }
                        }
                        //postDelayed(runnable, delayMs)
                    }
                    /*val handler: Handler
                    handler.postDelayed(it.dismiss(), 3000)*/
                })*/
                .setCancelable(false)
                .build()

            Handler().apply {
                val runnable = object : Runnable {
                    override fun run() {
                        dialog.show()
                        Log.i("Handler", "Ejecutado nuevamente")
                        dialog.dismiss()
                        postDelayed(this, 3000)
                    }
                }
                //postDelayed(runnable, delayMs)
            }*/

            /*val intent = Intent(this,GameActivity::class.java)
            intent.putExtra("personaje",personaje)
            intent.putExtra("username",username)
            startActivity(intent)*/
        }

        btnIndividual.setOnClickListener {
            val username = intent.getStringExtra("username")
            val intent = Intent(this,GameActivity::class.java)
            intent.putExtra("personaje",personaje)
            intent.putExtra("username",username)
            startActivity(intent)
        }
    }
}