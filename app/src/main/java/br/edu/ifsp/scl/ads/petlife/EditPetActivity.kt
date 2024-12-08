package br.edu.ifsp.scl.ads.petlife

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditPetBinding

class EditPetActivity : AppCompatActivity() {
    private val aepb: ActivityEditPetBinding by lazy {
        ActivityEditPetBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Define o layout
        setContentView(aepb.root)

        //Configura o spinner tipos
        val tiposPet = arrayOf(getString(R.string.tipo_pet_cao), getString(R.string.tipo_pet_gato))
        val adapterTipo = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposPet)
        aepb.tipoPetSp.adapter = adapterTipo

        //Configura o spinner portes
        val portesPet = arrayOf(getString(R.string.porte_pet_pequeno), getString(R.string.porte_pet_medio), getString(R.string.porte_pet_grande))
        val adapterPorte = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, portesPet)
        aepb.portePetSp.adapter = adapterPorte

        //Recebe os dados do pet da Intent
        val nome = intent.getStringExtra("nome") ?: ""
        val dataNascimento = intent.getStringExtra("dataNascimento") ?: ""
        val tipo = intent.getStringExtra("tipo") ?: ""
        val cor = intent.getStringExtra("cor") ?: ""
        val porte = intent.getStringExtra("porte") ?: ""

        //Preenche os campos com os dados
        aepb.nomePetEt.setText(nome)
        aepb.dataNascimentoPetEt.setText(dataNascimento)
        aepb.corPetEt.setText(cor)

        //Coloca a seleção certa no spinner
        val positionTipo = adapterTipo.getPosition(tipo)
        aepb.tipoPetSp.setSelection(positionTipo)

        val positionPorte = adapterPorte.getPosition(porte)
        aepb.portePetSp.setSelection(positionPorte)

        //Configura o botão de salvar para retornar os dados atualizados
        aepb.salvarBtn.setOnClickListener {
            val intent = intent.apply {
                putExtra("nome", aepb.nomePetEt.text.toString())
                putExtra("dataNascimento", aepb.dataNascimentoPetEt.text.toString())
                putExtra("tipo", aepb.tipoPetSp.selectedItem.toString())
                putExtra("cor", aepb.corPetEt.text.toString())
                putExtra("porte", aepb.portePetSp.selectedItem.toString())
            }
            setResult(RESULT_OK, intent)
            finish()  //Finaliza a activity
        }
    }
}
