package br.edu.ifsp.scl.ads.petlife.model

import br.edu.ifsp.scl.ads.petlife.Pet

interface PetDao {
    fun createPet(pet: Pet): Long
    fun retrievePets(): MutableList<Pet>
}