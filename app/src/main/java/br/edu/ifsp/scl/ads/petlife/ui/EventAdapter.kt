package br.edu.ifsp.scl.ads.petlife.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.petlife.R
import br.edu.ifsp.scl.ads.petlife.model.Event

// Adapter para exibir os eventos em um listview
class EventAdapter(context: Context, private val events: MutableList<Event>) :
    ArrayAdapter<Event>(context, R.layout.tile_event, events) {

    // Método responsável pela criação/atualização das views de cada item da lista
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Reutiliza uma view existente ou infla uma nova
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.tile_event, parent, false)
        val event = events[position]

        // Encontra os textviews para exibir os dados dos eventos
        val typeTv = view.findViewById<TextView>(R.id.typeTv)
        val dateTv = view.findViewById<TextView>(R.id.dateTv)

        // Define os valores dos TextViews com os dados
        typeTv.text = event.type
        dateTv.text = event.date

        // Retorna a view
        return view
    }
}
