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
    // ViewBinding para acessar os elementos do layout
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Controller para operações com pets
    private val mainController by lazy { MainController(this) }
    private val petList = mutableListOf<Pet>() // Lista de pets para exibição
    private val petAdapter by lazy { PetAdapter(this, petList) }

    // Launcher para tratar o resultado da tela de edição de pet
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
                    // Adiciona um novo pet
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
                    // Atualiza um pet existente
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
                loadPets() // Recarrega a lista após mudanças
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        // Configura a toolbar
        setSupportActionBar(amb.toolbarTb)
        supportActionBar?.title = getString(R.string.app_name)

        // Configura o ListView com o adapter
        amb.petLv.adapter = petAdapter
        loadPets() // Carrega os pets ao iniciar

        // Registra o menu de contexto no ListView
        registerForContextMenu(amb.petLv)

        // Clique curto para abrir a tela de eventos do pet
        amb.petLv.setOnItemClickListener { _, _, position, _ ->
            val selectedPet = petList[position]
            val intent = Intent(this, EventActivity::class.java).apply {
                putExtra("id", selectedPet.id) // Passa o ID do pet
                putExtra("nome", selectedPet.nome) // Passa o nome do pet
            }
            startActivity(intent)
        }

        // Botão para adicionar um novo pet
        amb.addPetBtn.setOnClickListener {
            val intent = Intent(this, EditPetActivity::class.java)
            editPetLauncher.launch(intent)
        }
    }

    // Carrega os pets do banco e atualiza a lista
    private fun loadPets() {
        petList.clear()
        petList.addAll(mainController.getPets())
        petAdapter.notifyDataSetChanged()
    }

    // Cria o menu de contexto para o ListView
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_pet, menu)
    }

    // Trata os itens selecionados no menu de contexto
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedPet = petList[info.position]

        return when (item.itemId) {
            R.id.editPetMi -> {
                // Abre a tela de edição com os dados do pet
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
                // Remove o pet selecionado
                mainController.removePet(selectedPet.id)
                loadPets() // Recarrega a lista após a remoção
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
