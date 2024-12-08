package br.edu.ifsp.scl.ads.petlife.model

// Interface que define as operações para o pet
interface PetDao {
    fun createPet(pet: Pet): Long
    fun retrievePets(): MutableList<Pet>
    fun updatePet(pet: Pet): Int
    fun deletePet(id: Int): Int
}
