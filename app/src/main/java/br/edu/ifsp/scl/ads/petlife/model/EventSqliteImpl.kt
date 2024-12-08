package br.edu.ifsp.scl.ads.petlife.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class EventSqliteImpl(context: Context) : EventDao {
    companion object {
        private const val EVENT_TABLE = "event"
        private const val EVENT_ID_COLUMN = "event_id"
        private const val PET_ID_COLUMN = "pet_id"
        private const val EVENT_TYPE_COLUMN = "type"
        private const val EVENT_DATE_COLUMN = "date"
        private const val EVENT_DESCRIPTION_COLUMN = "description"

        private const val CREATE_EVENT_TABLE_STATEMENT = """
            CREATE TABLE IF NOT EXISTS $EVENT_TABLE (
                $EVENT_ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT,
                $PET_ID_COLUMN INTEGER NOT NULL,
                $EVENT_TYPE_COLUMN TEXT NOT NULL,
                $EVENT_DATE_COLUMN TEXT NOT NULL,
                $EVENT_DESCRIPTION_COLUMN TEXT,
                FOREIGN KEY($PET_ID_COLUMN) REFERENCES pet(id) ON DELETE CASCADE
            );
        """
    }

    private val database: SQLiteDatabase = context.openOrCreateDatabase(
        "pets.db", Context.MODE_PRIVATE, null
    )

    init {
        try {
            database.execSQL(CREATE_EVENT_TABLE_STATEMENT)
        } catch (e: Exception) {
            Log.e("EventSqliteImpl", "Error creating database: ${e.message}")
        }
    }

    override fun createEvent(event: Event): Long {
        val values = ContentValues().apply {
            put(PET_ID_COLUMN, event.petId)
            put(EVENT_TYPE_COLUMN, event.type)
            put(EVENT_DATE_COLUMN, event.date)
            put(EVENT_DESCRIPTION_COLUMN, event.description)
        }
        return database.insert(EVENT_TABLE, null, values)
    }

    override fun retrieveEvents(petId: Int): MutableList<Event> {
        val events = mutableListOf<Event>()
        val cursor = database.rawQuery("SELECT * FROM $EVENT_TABLE WHERE $PET_ID_COLUMN = ?", arrayOf(petId.toString()))
        while (cursor.moveToNext()) {
            events.add(
                Event(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(EVENT_ID_COLUMN)),
                    petId = cursor.getInt(cursor.getColumnIndexOrThrow(PET_ID_COLUMN)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(EVENT_TYPE_COLUMN)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(EVENT_DATE_COLUMN)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(EVENT_DESCRIPTION_COLUMN))
                )
            )
        }
        cursor.close()
        return events
    }
}
