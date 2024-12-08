package br.edu.ifsp.scl.ads.petlife.controller

import br.edu.ifsp.scl.ads.petlife.model.Pet
import br.edu.ifsp.scl.ads.petlife.model.PetDao
import br.edu.ifsp.scl.ads.petlife.model.PetSqliteImpl
import br.edu.ifsp.scl.ads.petlife.ui.MainActivity

// Controller que controla as operações do pet
class MainController(mainActivity: MainActivity) {
    // Instancia o DAO
    private val petDao: PetDao = PetSqliteImpl(mainActivity)

    fun insertPet(pet: Pet) = petDao.createPet(pet)
    fun getPets(): MutableList<Pet> = petDao.retrievePets()
    fun updatePet(pet: Pet) = petDao.updatePet(pet)
    fun removePet(id: Int) = petDao.deletePet(id)
}
