package br.edu.ifsp.scl.ads.petlife

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        setSupportActionBar(amb.toolbarTb)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
            subtitle = this@MainActivity.javaClass.simpleName
        }

        val pet = Pet(
            nome = "Atlas",
            dataNascimento = "10/10/2020",
            tipo = "Cachorro",
            cor = "Marrom",
            porte = "Médio",
            ultimaVisitaVeterinario = "01/09/2024",
            ultimaVacina = "15/08/2024",
            ultimaIdaPetshop = "10/09/2024"
        )

        exibirInformacoesPet(pet)
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