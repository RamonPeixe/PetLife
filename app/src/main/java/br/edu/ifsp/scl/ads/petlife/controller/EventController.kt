package br.edu.ifsp.scl.ads.petlife.controller

import br.edu.ifsp.scl.ads.petlife.model.Event
import br.edu.ifsp.scl.ads.petlife.model.EventDao
import br.edu.ifsp.scl.ads.petlife.model.EventSqliteImpl
import br.edu.ifsp.scl.ads.petlife.ui.EventActivity

// Controller que controla as operações do evento
class EventController(eventActivity: EventActivity) {
    // Instancia o DAO
    private val eventDao: EventDao = EventSqliteImpl(eventActivity)

    fun addEvent(event: Event): Long = eventDao.createEvent(event)
    fun getEvents(petId: Int): MutableList<Event> = eventDao.retrieveEvents(petId)
    fun updateEvent(event: Event): Int = eventDao.updateEvent(event)
    fun removeEvent(eventId: Int): Int = eventDao.deleteEvent(eventId)
}
