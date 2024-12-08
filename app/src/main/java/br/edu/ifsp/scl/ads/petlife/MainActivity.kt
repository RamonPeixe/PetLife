package br.edu.ifsp.scl.ads.petlife

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.petlife.model.PetSqliteImpl
import br.edu.ifsp.scl.ads.petlife.ui.PetAdapter

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val petDatabase by lazy { PetSqliteImpl(this) }
    private val petList = mutableListOf<Pet>()
    private val petAdapter by lazy { PetAdapter(this, petList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarTb)
        supportActionBar?.title = getString(R.string.app_name)

        amb.petLv.adapter = petAdapter
        loadPets()

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
                    petDatabase.createPet(pet)
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
        petList.addAll(petDatabase.retrievePets())
        petAdapter.notifyDataSetChanged()
    }
}
