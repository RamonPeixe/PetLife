package br.edu.ifsp.scl.ads.petlife

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var pet: Pet

    private lateinit var editPetLauncher: ActivityResultLauncher<Intent>
    private lateinit var editVeterinarioLauncher: ActivityResultLauncher<Intent>
    private lateinit var editVacinaLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        setSupportActionBar(amb.toolbarTb)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
            subtitle = this@MainActivity.javaClass.simpleName
        }

        pet = Pet(
            nome = "Atlas",
            dataNascimento = "10/10/2020",
            tipo = "Cachorro",
            cor = "Preto e Branco",
            porte = "Médio",
            ultimaVisitaVeterinario = "01/09/2024",
            ultimaVacina = "15/08/2024",
            ultimaIdaPetshop = "10/09/2024"
        )

        exibirInformacoesPet(pet)

        editPetLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    pet = pet.copy(
                        nome = it.getStringExtra("nome") ?: pet.nome,
                        dataNascimento = it.getStringExtra("dataNascimento") ?: pet.dataNascimento,
                        tipo = it.getStringExtra("tipo") ?: pet.tipo,
                        cor = it.getStringExtra("cor") ?: pet.cor,
                        porte = it.getStringExtra("porte") ?: pet.porte
                    )
                    exibirInformacoesPet(pet)
                }
            }
        }

        editVeterinarioLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    pet = pet.copy(
                        ultimaVisitaVeterinario = it.getStringExtra("ultimaVisitaVeterinario")
                            ?: pet.ultimaVisitaVeterinario
                    )
                    exibirInformacoesPet(pet)
                }
            }
        }

        editVacinaLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    pet = pet.copy(
                        ultimaVacina = it.getStringExtra("ultimaVacina") ?: pet.ultimaVacina
                    )
                    exibirInformacoesPet(pet)
                }
            }
        }

        amb.editarDadosPetBtn.setOnClickListener {
            val intent = Intent(this, EditPetActivity::class.java).apply {
                putExtra("nome", pet.nome)
                putExtra("dataNascimento", pet.dataNascimento)
                putExtra("tipo", pet.tipo)
                putExtra("cor", pet.cor)
                putExtra("porte", pet.porte)
            }
            editPetLauncher.launch(intent)
        }

        amb.editarVeterinarioBtn.setOnClickListener {
            val intent = Intent(this, EditVeterinarioActivity::class.java).apply {
                putExtra("ultimaVisitaVeterinario", pet.ultimaVisitaVeterinario)
            }
            editVeterinarioLauncher.launch(intent)
        }

        amb.editarVacinaBtn.setOnClickListener {
            val intent = Intent(this, EditVacinaActivity::class.java).apply {
                putExtra("ultimaVacina", pet.ultimaVacina)
            }
            editVacinaLauncher.launch(intent)
        }
    }

    private fun exibirInformacoesPet(pet: Pet) {
        amb.nomePetTv.text = "Nome do Pet: ${pet.nome}"
        amb.dataNascimentoTv.text = "Data de Nascimento: ${pet.dataNascimento}"
        amb.tipoPetTv.text = "Tipo: ${pet.tipo}"
        amb.corPetTv.text = "Cor: ${pet.cor}"
        amb.portePetTv.text = "Porte: ${pet.porte}"
        amb.ultimaVisitaVeterinarioTv.text = "Última visita ao veterinário: ${pet.ultimaVisitaVeterinario}"
        amb.ultimaVacinaTv.text = "Última vacinação: ${pet.ultimaVacina}"
        amb.ultimaIdaPetshopTv.text = "Última ida ao petshop: ${pet.ultimaIdaPetshop}"
    }
}
