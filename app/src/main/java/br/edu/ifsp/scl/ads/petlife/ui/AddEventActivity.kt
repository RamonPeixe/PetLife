package br.edu.ifsp.scl.ads.petlife.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityAddEventBinding

class AddEventActivity : AppCompatActivity() {
    private val aevb: ActivityAddEventBinding by lazy {
        ActivityAddEventBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(aevb.root)

        // Configurações do Spinner
        val eventTypes = arrayOf("Visita ao Veterinário", "Visita ao Petshop", "Vacinação")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, eventTypes)
        aevb.eventTypeSp.adapter = adapter

        // Botão salvar com validação
        aevb.saveEventBtn.setOnClickListener {
            if (validateFields()) {
                val eventType = aevb.eventTypeSp.selectedItem.toString()
                val eventDate = aevb.eventDateEt.text.toString()
                val eventDescription = aevb.eventDescriptionEt.text.toString()

                // Retorna o evento criado para a EventActivity
                val resultIntent = intent.apply {
                    putExtra("eventType", eventType)
                    putExtra("eventDate", eventDate)
                    putExtra("eventDescription", eventDescription)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    // Valida os campos
    private fun validateFields(): Boolean {
        return when {
            aevb.eventTypeSp.selectedItem == null -> {
                showError("O tipo do evento deve ser selecionado")
                false
            }
            aevb.eventDateEt.text.isNullOrEmpty() -> {
                showError("A data do evento é obrigatória")
                false
            }
            aevb.eventDescriptionEt.text.isNullOrEmpty() -> {
                showError("A descrição do evento é obrigatória")
                false
            }
            else -> true
        }
    }

    // Exibe mensagem de erro
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
