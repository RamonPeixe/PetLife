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

    //Cria objeto Pet
    private lateinit var pet: Pet

    //Launchers
    private lateinit var editPetLauncher: ActivityResultLauncher<Intent>
    private lateinit var editVeterinarioLauncher: ActivityResultLauncher<Intent>
    private lateinit var editVacinaLauncher: ActivityResultLauncher<Intent>
    private lateinit var editPetshopLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Define o layout
        setContentView(amb.root)
        //Configura a toolbar
        setSupportActionBar(amb.toolbarTb)

        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }

        //Inicializa os dados do pet pré definido
        pet = Pet(
            nome = "Atlas",
            dataNascimento = "10/10/2020",
            tipo = "Cão",
            cor = "Preto e Branco",
            porte = "Médio",
            ultimaVisitaVeterinario = "01/09/2024",
            ultimaVacina = "15/08/2024",
            ultimaIdaPetshop = "10/09/2024"
        )

        exibirInformacoesPet(pet) //Exibe os dados do pet

        //Configura o launcher para editar os dados do pet
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
                    exibirInformacoesPet(pet) //Atualiza os dados depois da edição
                }
            }
        }

        //Configura o launcher para editar a última visita ao veterinário
        editVeterinarioLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    pet = pet.copy(
                        ultimaVisitaVeterinario = it.getStringExtra("ultimaVisitaVeterinario") ?: pet.ultimaVisitaVeterinario
                    )
                    exibirInformacoesPet(pet) //Atualiza os dados depois da edição
                }
            }
        }

        //Configura o launcher para editar a última vacina
        editVacinaLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    pet = pet.copy(
                        ultimaVacina = it.getStringExtra("ultimaVacina") ?: pet.ultimaVacina
                    )
                    exibirInformacoesPet(pet) //Atualiza os dados depois da edição
                }
            }
        }

        //Configura o launcher para editar a última ida ao petshop
        editPetshopLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    pet = pet.copy(
                        ultimaIdaPetshop = it.getStringExtra("ultimaIdaPetshop") ?: pet.ultimaIdaPetshop
                    )
                    exibirInformacoesPet(pet) //Atualiza os dados depois da edição
                }
            }
        }

        //Configura o botão para editar os dados do pet
        amb.editarDadosPetBtn.setOnClickListener {
            val intent = Intent(this, EditPetActivity::class.java).apply {
                putExtra("nome", pet.nome)
                putExtra("dataNascimento", pet.dataNascimento)
                putExtra("tipo", pet.tipo)
                putExtra("cor", pet.cor)
                putExtra("porte", pet.porte)
            }
            editPetLauncher.launch(intent) //Chama a activity para editar
        }

        //Configura o botão para editar a última visita ao veterinário
        amb.editarVeterinarioBtn.setOnClickListener {
            val intent = Intent(this, EditVeterinarioActivity::class.java).apply {
                putExtra("ultimaVisitaVeterinario", pet.ultimaVisitaVeterinario)
            }
            editVeterinarioLauncher.launch(intent) //Chama a activity para editar
        }

        //Configura o botão para editar a última vacina
        amb.editarVacinaBtn.setOnClickListener {
            val intent = Intent(this, EditVacinaActivity::class.java).apply {
                putExtra("ultimaVacina", pet.ultimaVacina)
            }
            editVacinaLauncher.launch(intent) //Chama a activity para editar
        }

        //Configura o botão para editar a última ida ao petshop
        amb.editarPetshopBtn.setOnClickListener {
            val intent = Intent(this, EditPetshopActivity::class.java).apply {
                putExtra("ultimaIdaPetshop", pet.ultimaIdaPetshop)
            }
            editPetshopLauncher.launch(intent) //Chama a activity para editar
        }
    }

    //Função para exibir as informações do pet
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

    // Teste de commit
}
