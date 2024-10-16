package br.edu.ifsp.scl.ads.petlife

data class Pet(
    val nome: String,
    val dataNascimento: String,
    val tipo: String,
    val cor: String,
    val porte: String,
    val ultimaVisitaVeterinario: String,
    val ultimaVacina: String,
    val ultimaIdaPetshop: String
)