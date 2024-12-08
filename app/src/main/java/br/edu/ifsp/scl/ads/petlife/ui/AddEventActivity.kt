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

        // Recebe dados se for uma edição
        val eventId = intent.getIntExtra("id", -1)
        val petId = intent.getIntExtra("petId", -1)
        val eventType = intent.getStringExtra("eventType") ?: ""
        val eventDate = intent.getStringExtra("eventDate") ?: ""
        val eventDescription = intent.getStringExtra("eventDescription") ?: ""

        aevb.eventDateEt.setText(eventDate)
        aevb.eventDescriptionEt.setText(eventDescription)
        aevb.eventTypeSp.setSelection(eventTypes.indexOf(eventType))

        // Botão salvar com validação
        aevb.saveEventBtn.setOnClickListener {
            if (validateFields()) {
                val type = aevb.eventTypeSp.selectedItem.toString()
                val date = aevb.eventDateEt.text.toString()
                val description = aevb.eventDescriptionEt.text.toString()

                // Retorna o evento criado/editado
                val resultIntent = intent.apply {
                    putExtra("id", eventId)
                    putExtra("eventType", type)
                    putExtra("eventDate", date)
                    putExtra("eventDescription", description)
                    putExtra("petId", petId)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

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

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
