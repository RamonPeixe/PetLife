package br.edu.ifsp.scl.ads.petlife.controller

import br.edu.ifsp.scl.ads.petlife.model.Event
import br.edu.ifsp.scl.ads.petlife.model.EventDao
import br.edu.ifsp.scl.ads.petlife.model.EventSqliteImpl
import br.edu.ifsp.scl.ads.petlife.ui.EventActivity

class EventController(eventActivity: EventActivity) {
    private val eventDao: EventDao = EventSqliteImpl(eventActivity)

    fun addEvent(event: Event) = eventDao.createEvent(event)
    fun getEvents(petId: Int): MutableList<Event> = eventDao.retrieveEvents(petId)
}
