package br.edu.ifsp.scl.ads.petlife.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.petlife.R
import br.edu.ifsp.scl.ads.petlife.model.Event

class EventAdapter(context: Context, private val events: MutableList<Event>) :
    ArrayAdapter<Event>(context, R.layout.tile_event, events) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.tile_event, parent, false)
        val event = events[position]

        val typeTv = view.findViewById<TextView>(R.id.eventTypeTv)
        val dateTv = view.findViewById<TextView>(R.id.eventDateTv)

        typeTv.text = event.type
        dateTv.text = event.date

        return view
    }
}
