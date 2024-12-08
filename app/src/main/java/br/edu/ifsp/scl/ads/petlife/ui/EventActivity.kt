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
    // ViewBinding para acessar os elementos do layout
    private val aeb: ActivityEventBinding by lazy {
        ActivityEventBinding.inflate(layoutInflater)
    }

    // Controller para operações com eventos
    private val eventController by lazy { EventController(this) }
    private val eventList = mutableListOf<Event>() // Lista de eventos para exibição
    private val eventAdapter by lazy { EventAdapter(this, eventList) }

    // Launcher para tratar o resultado da tela de edição de eventos
    private val addEventLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                val eventId = it.getIntExtra("id", -1)
                val eventType = it.getStringExtra("eventType") ?: ""
                val eventDate = it.getStringExtra("eventDate") ?: ""
                val eventDescription = it.getStringExtra("eventDescription") ?: ""
                val petId = intent.getIntExtra("id", -1)

                if (petId != -1) {
                    if (eventId == -1) {
                        // Adiciona um novo evento
                        eventController.addEvent(
                            Event(
                                petId = petId,
                                type = eventType,
                                date = eventDate,
                                description = eventDescription
                            )
                        )
                    } else {
                        // Atualiza um evento existente
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
                    loadEvents(petId) // Recarrega a lista após mudanças
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(aeb.root)

        // Obtém o nome do pet
        val petName = intent.getStringExtra("nome") ?: "Desconhecido"
        val petId = intent.getIntExtra("id", -1)

        // Configura a toolbar
        setSupportActionBar(aeb.toolbarTb)
        supportActionBar?.title = getString(R.string.event_list)
        aeb.petNameTv.text = getString(R.string.events_of, petName)

        // Configura o ListView com o adapter
        aeb.eventLv.adapter = eventAdapter
        registerForContextMenu(aeb.eventLv) // Registra o menu de contexto

        // Carrega os eventos relacionados ao pet
        if (petId != -1) {
            loadEvents(petId)
        }

        // Botão para adicionar um novo evento
        aeb.addEventBtn.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            addEventLauncher.launch(intent)
        }
    }

    // Carrega os eventos do banco e atualiza a lista
    private fun loadEvents(petId: Int) {
        eventList.clear()
        eventList.addAll(eventController.getEvents(petId))
        eventAdapter.notifyDataSetChanged()
    }

    // Cria o menu de contexto para o ListView
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_event, menu)
    }

    // Trata os itens selecionados no menu de contexto
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedEvent = eventList[info.position]

        return when (item.itemId) {
            R.id.editEventMi -> {
                // Abre a tela de edição com os dados do evento
                val intent = Intent(this, AddEventActivity::class.java).apply {
                    putExtra("id", selectedEvent.id)
                    putExtra("eventType", selectedEvent.type)
                    putExtra("eventDate", selectedEvent.date)
                    putExtra("eventDescription", selectedEvent.description)
                }
                addEventLauncher.launch(intent)
                true
            }
            R.id.removeEventMi -> {
                // Remove o evento selecionado
                eventController.removeEvent(selectedEvent.id)
                loadEvents(selectedEvent.petId) // Recarrega a lista após remoção
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
