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
        setContentView(aepb.root)

        val tiposPet = arrayOf("Cão", "Gato")
        val adapterTipo = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposPet)
        aepb.tipoPetSp.adapter = adapterTipo

        val portesPet = arrayOf("Pequeno", "Médio", "Grande")
        val adapterPorte = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, portesPet)
        aepb.portePetSp.adapter = adapterPorte

        val nome = intent.getStringExtra("nome") ?: "Nome não informado"
        val dataNascimento = intent.getStringExtra("dataNascimento") ?: "Data não informada"
        val tipo = intent.getStringExtra("tipo") ?: "Tipo não informado"
        val cor = intent.getStringExtra("cor") ?: "Cor não informada"
        val porte = intent.getStringExtra("porte") ?: "Porte não informado"

        aepb.nomePetEt.setText(nome)
        aepb.dataNascimentoPetEt.setText(dataNascimento)
        aepb.corPetEt.setText(cor)

        val positionTipo = adapterTipo.getPosition(tipo)
        aepb.tipoPetSp.setSelection(positionTipo)

        val positionPorte = adapterPorte.getPosition(porte)
        aepb.portePetSp.setSelection(positionPorte)

        aepb.salvarBtn.setOnClickListener {
            val intent = intent.apply {
                putExtra("nome", aepb.nomePetEt.text.toString())
                putExtra("dataNascimento", aepb.dataNascimentoPetEt.text.toString())
                putExtra("tipo", aepb.tipoPetSp.selectedItem.toString())
                putExtra("cor", aepb.corPetEt.text.toString())
                putExtra("porte", aepb.portePetSp.selectedItem.toString())
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
