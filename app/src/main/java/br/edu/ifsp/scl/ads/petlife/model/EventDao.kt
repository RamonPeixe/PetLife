package br.edu.ifsp.scl.ads.petlife.model

// Interface que define as operações para o evento
interface EventDao {
    fun createEvent(event: Event): Long
    fun retrieveEvents(petId: Int): MutableList<Event>
    fun updateEvent(event: Event): Int
    fun deleteEvent(eventId: Int): Int
}

