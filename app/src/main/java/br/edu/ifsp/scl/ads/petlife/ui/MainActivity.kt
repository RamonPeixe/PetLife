package br.edu.ifsp.scl.ads.petlife.ui

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.R
import br.edu.ifsp.scl.ads.petlife.model.Pet
import br.edu.ifsp.scl.ads.petlife.controller.MainController
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainController by lazy { MainController(this) }
    private val petList = mutableListOf<Pet>()
    private val petAdapter by lazy { PetAdapter(this, petList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarTb)
        supportActionBar?.title = getString(R.string.app_name)

        amb.petLv.adapter = petAdapter
        loadPets()

        registerForContextMenu(amb.petLv)

        // Clique rápido para abrir EventActivity
        amb.petLv.setOnItemClickListener { _, _, position, _ ->
            val selectedPet = petList[position]
            val intent = Intent(this, EventActivity::class.java).apply {
                putExtra("petName", selectedPet.nome)
                putExtra("petType", selectedPet.tipo)
            }
            startActivity(intent)
        }

        // Clique para adicionar pet
        val editPetLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    val pet = Pet(
                        nome = it.getStringExtra("nome") ?: "",
                        tipo = it.getStringExtra("tipo") ?: "",
                        dataNascimento = "",
                        cor = "",
                        porte = "",
                        ultimaVisitaVeterinario = "",
                        ultimaVacina = "",
                        ultimaIdaPetshop = ""
                    )
                    mainController.insertPet(pet)
                    loadPets()
                }
            }
        }

        amb.addPetBtn.setOnClickListener {
            val intent = Intent(this, EditPetActivity::class.java)
            editPetLauncher.launch(intent)
        }
    }

    private fun loadPets() {
        petList.clear()
        petList.addAll(mainController.getPets())
        petAdapter.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_pet, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedPet = petList[info.position]

        return when (item.itemId) {
            R.id.editPetMi -> {
                val intent = Intent(this, EditPetActivity::class.java).apply {
                    putExtra("nome", selectedPet.nome)
                    putExtra("tipo", selectedPet.tipo)
                }
                startActivity(intent)
                true
            }
            R.id.removePetMi -> {
                //mainController.removePet(selectedPet.nome)
                loadPets()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
