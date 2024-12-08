package br.edu.ifsp.scl.ads.petlife.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.petlife.model.Pet
import br.edu.ifsp.scl.ads.petlife.R

// Adapter para exibir os pets em um listview
class PetAdapter(context: Context, private val pets: MutableList<Pet>) :
    ArrayAdapter<Pet>(context, R.layout.tile_pet, pets) {

    // Método responsável pela criação/atualização das views de cada item da lista
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Reutiliza uma view existente ou infla uma nova
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.tile_pet, parent, false)
        val pet = pets[position]

        // Encontra os textviews para exibir os dados dos eventos
        val nameTv = view.findViewById<TextView>(R.id.nameTv)
        val typeTv = view.findViewById<TextView>(R.id.typeTv)

        // Define os valores dos TextViews com os dados do pet
        nameTv.text = pet.nome
        typeTv.text = pet.tipo

        // Retorna a view
        return view
    }
}
