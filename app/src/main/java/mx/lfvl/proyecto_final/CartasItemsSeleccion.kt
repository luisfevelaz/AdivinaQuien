package mx.lfvl.proyecto_final

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_personaje.view.*

class CartasItemsSeleccion(private var datos: Array<Personaje>,private val arrayOriginal: Array<Personaje>,
                            private val clickListener : (Personaje) -> Unit):
    RecyclerView.Adapter<CartasItemsSeleccion.PersonajesViewHolder>(){


        class PersonajesViewHolder(val item: View): RecyclerView.ViewHolder(item){
            val img = item.findViewById(R.id.imagen) as ImageView
            fun bindPersonaje(personaje: Personaje){
                img.setImageResource(personaje.imagen);
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajesViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_personaje, parent, false) as LinearLayout
        return PersonajesViewHolder(item)
    }

    override fun onBindViewHolder(holder: PersonajesViewHolder, position: Int) {
        val personaje = datos[position]

        holder.bindPersonaje(personaje)
        holder.item.setOnClickListener {
            var personaje = datos[position]

            if(datos[position].presionado){
                datos[position].imagen = arrayOriginal[position].imagen;
                personaje.imagen = arrayOriginal[position].imagen;
                datos[position].presionado = false
            }else{
                datos[position].imagen = R.drawable.atras1
                datos[position].presionado = true
                personaje.imagen = R.drawable.atras1
            }
            holder.bindPersonaje(personaje)

        }
    }

    override fun getItemCount() = datos.size

}