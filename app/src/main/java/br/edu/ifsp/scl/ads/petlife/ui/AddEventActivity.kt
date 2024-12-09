package br.edu.ifsp.scl.ads.petlife.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
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

        // Configuração do Spinner com as opções de tipos de evento
        val eventTypes = arrayOf("Visita ao Veterinário", "Visita ao Petshop", "Vacinação", "Remédio")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, eventTypes)
        aevb.eventTypeSp.adapter = adapter

        aevb.eventTypeSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedType = eventTypes[position]
                if (selectedType == "Remédio") {
                    aevb.timeEventEt.visibility = View.VISIBLE
                } else {
                    aevb.timeEventEt.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                aevb.timeEventEt.visibility = View.GONE
            }
        }

        // Verifica se é edição e preenche os campos existentes
        val eventId = intent.getIntExtra("id", -1)
        val eventType = intent.getStringExtra("eventType") ?: ""
        val eventDate = intent.getStringExtra("eventDate") ?: ""
        val eventDescription = intent.getStringExtra("eventDescription") ?: ""
        val eventTime = intent.getStringExtra("eventTime") ?: ""

        if (eventId != -1) { // Caso seja uma edição
            aevb.eventTypeSp.setSelection(adapter.getPosition(eventType))
            aevb.eventDateEt.setText(eventDate)
            aevb.eventDescriptionEt.setText(eventDescription)
            if (eventType == "Remédio") {
                aevb.timeEventEt.setText(eventTime)
                aevb.timeEventEt.visibility = View.VISIBLE
            }
        }

        // Configura o botão de salvar
        aevb.saveEventBtn.setOnClickListener {
            if (validateFields()) {
                val selectedType = aevb.eventTypeSp.selectedItem.toString()
                val eventDateInput = aevb.eventDateEt.text.toString()
                val eventDescriptionInput = aevb.eventDescriptionEt.text.toString()
                val eventTimeInput = if (selectedType == "Remédio") aevb.timeEventEt.text.toString() else null

                // Retorna os dados
                val resultIntent = intent.apply {
                    putExtra("id", eventId)
                    putExtra("eventType", selectedType)
                    putExtra("eventDate", eventDateInput)
                    putExtra("eventDescription", eventDescriptionInput)
                    putExtra("eventTime", eventTimeInput)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    // Validação dos campos obrigatórios
    private fun validateFields(): Boolean {
        return when {
            aevb.eventTypeSp.selectedItem == null -> {
                showError("Selecione o tipo de evento")
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
            aevb.timeEventEt.visibility == View.VISIBLE && aevb.timeEventEt.text.isNullOrEmpty() -> {
                showError("O horário do remédio é obrigatório")
                false
            }
            else -> true
        }
    }

    // Exibe uma mensagem de erro para o usuário
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
