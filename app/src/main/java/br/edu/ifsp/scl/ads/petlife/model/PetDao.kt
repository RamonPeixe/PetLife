package br.edu.ifsp.scl.ads.petlife.model

interface PetDao {
    fun createPet(pet: Pet): Long
    fun retrievePets(): MutableList<Pet>
    fun updatePet(pet: Pet): Int
    fun deletePet(id: Int): Int
}
