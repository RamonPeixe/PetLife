package br.edu.ifsp.scl.ads.petlife.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class PetSqliteImpl(context: Context) : PetDao {
    companion object {
        private const val PET_DATABASE_FILE = "pets.db"
        private const val PET_TABLE = "pet"
        private const val ID_COLUMN = "id"
        private const val NAME_COLUMN = "name"
        private const val TYPE_COLUMN = "type"

        private const val CREATE_PET_TABLE_STATEMENT = """
            CREATE TABLE IF NOT EXISTS $PET_TABLE (
                $ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT,
                $NAME_COLUMN TEXT NOT NULL,
                $TYPE_COLUMN TEXT NOT NULL
            );
        """
    }

    private val database: SQLiteDatabase = context.openOrCreateDatabase(
        PET_DATABASE_FILE, Context.MODE_PRIVATE, null
    )

    init {
        try {
            database.execSQL(CREATE_PET_TABLE_STATEMENT)
        } catch (e: Exception) {
            Log.e("PetLife", "Error creating database: ${e.message}")
        }
    }

    override fun createPet(pet: Pet): Long {
        val values = ContentValues().apply {
            put(NAME_COLUMN, pet.nome)
            put(TYPE_COLUMN, pet.tipo)
        }
        return database.insert(PET_TABLE, null, values)
    }

    override fun retrievePets(): MutableList<Pet> {
        val pets = mutableListOf<Pet>()
        val cursor = database.rawQuery("SELECT * FROM $PET_TABLE", null)

        while (cursor.moveToNext()) {
            pets.add(
                Pet(
                    nome = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COLUMN)),
                    tipo = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_COLUMN)),
                    dataNascimento = "",
                    cor = "",
                    porte = "",
                    ultimaVisitaVeterinario = "",
                    ultimaVacina = "",
                    ultimaIdaPetshop = ""
                )
            )
        }
        cursor.close()
        return pets
    }
}
