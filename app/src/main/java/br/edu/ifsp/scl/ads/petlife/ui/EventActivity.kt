package br.edu.ifsp.scl.ads.petlife.ui

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
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

    private val addEditEventLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                val eventId = it.getIntExtra("id", -1)
                val eventType = it.getStringExtra("eventType") ?: ""
                val eventDate = it.getStringExtra("eventDate") ?: ""
                val eventDescription = it.getStringExtra("eventDescription") ?: ""
                val petId = intent.getIntExtra("id", -1)

                if (eventId == -1) {
                    // Novo evento
                    eventController.addEvent(
                        Event(
                            petId = petId,
                            type = eventType,
                            date = eventDate,
                            description = eventDescription
                        )
                    )
                } else {
                    // Atualiza evento
                    eventController.updateEvent(
                        Event(
                            id = eventId,
                            petId = petId,
                            type = eventType,
                            date = eventDate,
                            description = eventDescription
                        )
                    )
                }
                loadEvents(petId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(aeb.root)

        // Configura o nome do pet
        val petName = intent.getStringExtra("nome") ?: "Desconhecido"
        val petId = intent.getIntExtra("id", -1)

        // Configura a toolbar
        setSupportActionBar(aeb.toolbarTb)
        supportActionBar?.title = getString(R.string.event_list)
        aeb.petNameTv.text = "Eventos de $petName"

        // Configura o ListView e registra o menu de contexto
        aeb.eventLv.adapter = eventAdapter
        registerForContextMenu(aeb.eventLv)

        // Carrega os eventos do pet
        if (petId != -1) {
            loadEvents(petId)
        }

        // Configura o botão de adicionar eventos
        aeb.addEventBtn.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            intent.putExtra("petId", petId)
            addEditEventLauncher.launch(intent)
        }
    }

    private fun loadEvents(petId: Int) {
        eventList.clear()
        eventList.addAll(eventController.getEvents(petId))
        eventAdapter.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_event, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedEvent = eventList[info.position]

        return when (item.itemId) {
            R.id.editEventMi -> {
                val intent = Intent(this, AddEventActivity::class.java).apply {
                    putExtra("id", selectedEvent.id)
                    putExtra("eventType", selectedEvent.type)
                    putExtra("eventDate", selectedEvent.date)
                    putExtra("eventDescription", selectedEvent.description)
                    putExtra("petId", selectedEvent.petId)
                }
                addEditEventLauncher.launch(intent)
                true
            }
            R.id.removeEventMi -> {
                //eventController.removeEvent(selectedEvent.id)
                loadEvents(selectedEvent.petId)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
