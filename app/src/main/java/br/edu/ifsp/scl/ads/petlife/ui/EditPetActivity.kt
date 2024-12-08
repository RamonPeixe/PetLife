package br.edu.ifsp.scl.ads.petlife.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditPetBinding

class EditPetActivity : AppCompatActivity() {
    private val aepb: ActivityEditPetBinding by lazy {
        ActivityEditPetBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(aepb.root)

        // Configurações dos spinners
        val tiposPet = arrayOf("Cão", "Gato")
        val adapterTipo = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposPet)
        aepb.tipoPetSp.adapter = adapterTipo

        val portesPet = arrayOf("Pequeno", "Médio", "Grande")
        val adapterPorte = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, portesPet)
        aepb.portePetSp.adapter = adapterPorte

        // Recebe os dados se for uma edição
        val id = intent.getIntExtra("id", -1)
        val nome = intent.getStringExtra("nome") ?: ""
        val dataNascimento = intent.getStringExtra("dataNascimento") ?: ""
        val tipo = intent.getStringExtra("tipo") ?: ""
        val cor = intent.getStringExtra("cor") ?: ""
        val porte = intent.getStringExtra("porte") ?: ""

        aepb.nomePetEt.setText(nome)
        aepb.dataNascimentoPetEt.setText(dataNascimento)
        aepb.corPetEt.setText(cor)
        aepb.tipoPetSp.setSelection(adapterTipo.getPosition(tipo))
        aepb.portePetSp.setSelection(adapterPorte.getPosition(porte))

        // Botão salvar com validação
        aepb.salvarBtn.setOnClickListener {
            if (validateFields()) {
                val intent = intent.apply {
                    putExtra("id", id)
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

    // validar campos
    private fun validateFields(): Boolean {
        return when {
            aepb.nomePetEt.text.isNullOrEmpty() -> {
                showError("Nome é obrigatório")
                false
            }
            aepb.dataNascimentoPetEt.text.isNullOrEmpty() -> {
                showError("Data de nascimento é obrigatória")
                false
            }
            aepb.tipoPetSp.selectedItem == null -> {
                showError("Tipo do pet deve ser selecionado")
                false
            }
            aepb.corPetEt.text.isNullOrEmpty() -> {
                showError("Cor é obrigatória")
                false
            }
            aepb.portePetSp.selectedItem == null -> {
                showError("Porte do pet deve ser selecionado")
                false
            }
            else -> true
        }
    }

    // exibe toast
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
