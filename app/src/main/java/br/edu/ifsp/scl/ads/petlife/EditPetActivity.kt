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
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposPet)
        aepb.tipoPetSp.adapter = adapter

        val nome = intent.getStringExtra("nome") ?: "Nome não informado"
        val dataNascimento = intent.getStringExtra("dataNascimento") ?: "Data não informada"
        val tipo = intent.getStringExtra("tipo") ?: "Tipo não informado"
        val cor = intent.getStringExtra("cor") ?: "Cor não informada"
        val porte = intent.getStringExtra("porte") ?: "Porte não informado"

        aepb.nomePetEt.setText(nome)
        aepb.dataNascimentoPetEt.setText(dataNascimento)
        aepb.corPetEt.setText(cor)
        aepb.portePetEt.setText(porte)

        val position = adapter.getPosition(tipo)
        aepb.tipoPetSp.setSelection(position)

        aepb.salvarBtn.setOnClickListener {
            val intent = intent.apply {
                putExtra("nome", aepb.nomePetEt.text.toString())
                putExtra("dataNascimento", aepb.dataNascimentoPetEt.text.toString())
                putExtra("tipo", aepb.tipoPetSp.selectedItem.toString())
                putExtra("cor", aepb.corPetEt.text.toString())
                putExtra("porte", aepb.portePetEt.text.toString())
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
