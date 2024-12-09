package br.edu.ifsp.scl.ads.petlife.model

//Define classe Event com os dados
data class Event(
    val id: Int = 0,
    val petId: Int,
    val type: String,
    val date: String,
    val description: String,
    val time: String? = null
)