package br.edu.ifsp.scl.ads.petlife.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.R
import br.edu.ifsp.scl.ads.petlife.controller.EventController
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEventBinding
import br.edu.ifsp.scl.ads.petlife.model.Event

class EventActivity : AppCompatActivity() {
    private val aeb: ActivityEventBinding by lazy {
        ActivityEventBinding.inflate(layoutInflater)
    }
    private val eventController by lazy { EventController(this) }
    private val eventList = mutableListOf<Event>()
    private val eventAdapter by lazy { EventAdapter(this, eventList) }
    private var petId: Int = 0

    private val addEventLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                val eventType = it.getStringExtra("eventType") ?: ""
                val eventDate = it.getStringExtra("eventDate") ?: ""
                val eventDescription = it.getStringExtra("eventDescription") ?: ""

                // Salva o evento no banco de dados e atualiza a lista
                val newEvent = Event(
                    petId = petId,
                    type = eventType,
                    date = eventDate,
                    description = eventDescription
                )
                eventController.addEvent(newEvent)
                loadEvents() // Atualiza a lista de eventos
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(aeb.root)

        // Obtém o nome do pet passado pela Intent
        val petName = intent.getStringExtra("nome") ?: "Desconhecido"
        petId = intent.getIntExtra("id", -1)

        // Configura o texto no TextView (Eventos de ...)
        aeb.petNameTv.text = "Eventos de $petName"

        // Configura a toolbar
        setSupportActionBar(aeb.toolbarTb)
        supportActionBar?.title = getString(R.string.event_list)

        // Configura o ListView e carrega os eventos
        aeb.eventLv.adapter = eventAdapter
        loadEvents()

        // Configura o botão de adicionar eventos
        aeb.addEventBtn.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java).apply {
                putExtra("petId", petId)
            }
            addEventLauncher.launch(intent)
        }
    }

    private fun loadEvents() {
        eventList.clear()
        eventList.addAll(eventController.getEvents(petId))
        eventAdapter.notifyDataSetChanged()
    }
}
