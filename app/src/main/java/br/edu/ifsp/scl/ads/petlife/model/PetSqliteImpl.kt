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
        private const val DATE_COLUMN = "date_of_birth"
        private const val TYPE_COLUMN = "type"
        private const val COLOR_COLUMN = "color"
        private const val SIZE_COLUMN = "size"

        private const val CREATE_PET_TABLE_STATEMENT = """
            CREATE TABLE IF NOT EXISTS $PET_TABLE (
                $ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT,
                $NAME_COLUMN TEXT NOT NULL,
                $DATE_COLUMN TEXT NOT NULL,
                $TYPE_COLUMN TEXT NOT NULL,
                $COLOR_COLUMN TEXT NOT NULL,
                $SIZE_COLUMN TEXT NOT NULL
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
            put(DATE_COLUMN, pet.dataNascimento)
            put(TYPE_COLUMN, pet.tipo)
            put(COLOR_COLUMN, pet.cor)
            put(SIZE_COLUMN, pet.porte)
        }
        return database.insert(PET_TABLE, null, values)
    }

    override fun retrievePets(): MutableList<Pet> {
        val pets = mutableListOf<Pet>()
        val cursor = database.rawQuery("SELECT * FROM $PET_TABLE", null)

        while (cursor.moveToNext()) {
            pets.add(
                Pet(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COLUMN)),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COLUMN)),
                    dataNascimento = cursor.getString(cursor.getColumnIndexOrThrow(DATE_COLUMN)),
                    tipo = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_COLUMN)),
                    cor = cursor.getString(cursor.getColumnIndexOrThrow(COLOR_COLUMN)),
                    porte = cursor.getString(cursor.getColumnIndexOrThrow(SIZE_COLUMN))
                )
            )
        }
        cursor.close()
        return pets
    }

    override fun updatePet(pet: Pet): Int {
        val values = ContentValues().apply {
            put(NAME_COLUMN, pet.nome)
            put(DATE_COLUMN, pet.dataNascimento)
            put(TYPE_COLUMN, pet.tipo)
            put(COLOR_COLUMN, pet.cor)
            put(SIZE_COLUMN, pet.porte)
        }
        return database.update(
            PET_TABLE,
            values,
            "$ID_COLUMN = ?",
            arrayOf(pet.id.toString())
        )
    }

    override fun deletePet(id: Int): Int {
        return database.delete(PET_TABLE, "$ID_COLUMN = ?", arrayOf(id.toString()))
    }
}
