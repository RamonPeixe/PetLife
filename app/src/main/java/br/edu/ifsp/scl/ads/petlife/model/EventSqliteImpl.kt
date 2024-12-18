package br.edu.ifsp.scl.ads.petlife.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class EventSqliteImpl(context: Context) : EventDao {
    companion object {
        private const val EVENT_DATABASE_FILE = "events.db"
        private const val EVENT_TABLE = "event"
        private const val ID_COLUMN = "id"
        private const val PET_ID_COLUMN = "pet_id"
        private const val TYPE_COLUMN = "type"
        private const val DATE_COLUMN = "date"
        private const val DESCRIPTION_COLUMN = "description"
        private const val TIME_COLUMN = "time"

        private const val CREATE_EVENT_TABLE_STATEMENT = """
            CREATE TABLE IF NOT EXISTS $EVENT_TABLE (
                $ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT,
                $PET_ID_COLUMN INTEGER NOT NULL,
                $TYPE_COLUMN TEXT NOT NULL,
                $DATE_COLUMN TEXT NOT NULL,
                $DESCRIPTION_COLUMN TEXT NOT NULL,
                $TIME_COLUMN TEXT,
                FOREIGN KEY($PET_ID_COLUMN) REFERENCES pet(id) ON DELETE CASCADE
            );
        """
    }

    private val database: SQLiteDatabase = context.openOrCreateDatabase(
        EVENT_DATABASE_FILE, Context.MODE_PRIVATE, null
    )

    init {
        try {
            database.execSQL(CREATE_EVENT_TABLE_STATEMENT)
        } catch (e: Exception) {
            Log.e("PetLife", "Error creating database: ${e.message}")
        }
    }

    override fun createEvent(event: Event): Long {
        val values = ContentValues().apply {
            put(PET_ID_COLUMN, event.petId)
            put(TYPE_COLUMN, event.type)
            put(DATE_COLUMN, event.date)
            put(DESCRIPTION_COLUMN, event.description)
            put(TIME_COLUMN, event.time)
        }
        return database.insert(EVENT_TABLE, null, values)
    }

    override fun retrieveEvents(petId: Int): MutableList<Event> {
        val events = mutableListOf<Event>()
        val cursor = database.rawQuery("SELECT * FROM $EVENT_TABLE WHERE $PET_ID_COLUMN = ?", arrayOf(petId.toString()))

        while (cursor.moveToNext()) {
            events.add(
                Event(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COLUMN)),
                    petId = cursor.getInt(cursor.getColumnIndexOrThrow(PET_ID_COLUMN)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_COLUMN)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(DATE_COLUMN)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION_COLUMN)),
                    time = cursor.getString(cursor.getColumnIndexOrThrow(TIME_COLUMN))
                )
            )
        }
        cursor.close()
        return events
    }

    override fun updateEvent(event: Event): Int {
        val values = ContentValues().apply {
            put(TYPE_COLUMN, event.type)
            put(DATE_COLUMN, event.date)
            put(DESCRIPTION_COLUMN, event.description)
            put(TIME_COLUMN, event.time)
        }
        return database.update(
            EVENT_TABLE,
            values,
            "$ID_COLUMN = ?",
            arrayOf(event.id.toString())
        )
    }

    override fun deleteEvent(eventId: Int): Int {
        return database.delete(EVENT_TABLE, "$ID_COLUMN = ?", arrayOf(eventId.toString()))
    }
}
