package mx.lfvl.proyecto_final

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_personaje.view.*

class CartasItemsSeleccion(private val contexto: Context, private val items: List<Personaje>):ArrayAdapter<Personaje>(contexto,0,items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // definimos la variable layout que nos va a ayudar a pasar los items de variables a presentarlas en el layout de item_concepto
        val layout = LayoutInflater.from(contexto).inflate(R.layout.item_personaje,parent,false)
        // concepto toma al item en la posición en turno para pasarle al layout sus propiedades y reflejarlas en pantalla
        val concepto = items[position]
        layout.nombre.text = concepto.nombre
        layout.imagen.setImageResource(concepto.imagen)
        return layout
    }
}