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
import br.edu.ifsp.scl.ads.petlife.controller.MainController
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.petlife.model.Pet

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainController by lazy { MainController(this) }
    private val petList = mutableListOf<Pet>()
    private val petAdapter by lazy { PetAdapter(this, petList) }

    private val editPetLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                val id = it.getIntExtra("id", -1)
                val nome = it.getStringExtra("nome") ?: ""
                val dataNascimento = it.getStringExtra("dataNascimento") ?: ""
                val tipo = it.getStringExtra("tipo") ?: ""
                val cor = it.getStringExtra("cor") ?: ""
                val porte = it.getStringExtra("porte") ?: ""

                if (id == -1) {
                    // Novo pet
                    mainController.insertPet(
                        Pet(
                            nome = nome,
                            dataNascimento = dataNascimento,
                            tipo = tipo,
                            cor = cor,
                            porte = porte
                        )
                    )
                } else {
                    // Atualiza pet
                    mainController.updatePet(
                        Pet(
                            id = id,
                            nome = nome,
                            dataNascimento = dataNascimento,
                            tipo = tipo,
                            cor = cor,
                            porte = porte
                        )
                    )
                }
                loadPets()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarTb)
        supportActionBar?.title = getString(R.string.app_name)

        amb.petLv.adapter = petAdapter
        loadPets()

        registerForContextMenu(amb.petLv)

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
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_pet, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedPet = petList[info.position]

        return when (item.itemId) {
            R.id.editPetMi -> {
                val intent = Intent(this, EditPetActivity::class.java).apply {
                    putExtra("id", selectedPet.id)
                    putExtra("nome", selectedPet.nome)
                    putExtra("dataNascimento", selectedPet.dataNascimento)
                    putExtra("tipo", selectedPet.tipo)
                    putExtra("cor", selectedPet.cor)
                    putExtra("porte", selectedPet.porte)
                }
                editPetLauncher.launch(intent)
                true
            }
            R.id.removePetMi -> {
                //mainController.removePet(selectedPet.id)
                loadPets()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
