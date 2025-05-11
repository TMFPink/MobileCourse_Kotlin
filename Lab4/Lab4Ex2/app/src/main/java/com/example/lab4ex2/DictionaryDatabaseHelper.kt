package com.example.lab4ex2


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DictionaryDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "dictionary.ex1"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "words"
        const val COLUMN_WORD = "word"
        const val COLUMN_MEANING = "meaning"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_WORD TEXT PRIMARY KEY," +
                    "$COLUMN_MEANING TEXT)"
        )

        // Insert some sample words when the database is first created
        insertInitialData(db)
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        insertWord(db, "verisimilitude", "The appearance of being true or real.")
        insertWord(db, "apple", "A round fruit with red or yellow skin.")
        insertWord(db, "application", "A formal request or a computer program.")
        insertWord(db, "apply", "Make a formal application or request.")
        insertWord(db, "banana", "A long curved fruit.")
    }

    private fun insertWord(db: SQLiteDatabase, word: String, meaning: String) {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_WORD, word)
        contentValues.put(COLUMN_MEANING, meaning)
        db.insert(TABLE_NAME, null, contentValues)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun findExactMeaning(word: String): String? {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_MEANING),
            "$COLUMN_WORD = ?",
            arrayOf(word),
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val meaning = cursor.getString(0)
            cursor.close()
            meaning
        } else {
            cursor.close()
            null
        }
    }

    fun findWordsContaining(substring: String): List<String> {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_WORD),
            "$COLUMN_WORD LIKE ?",
            arrayOf("%$substring%"),
            null, null, null
        )

        val words = mutableListOf<String>()
        if (cursor.moveToFirst()) {
            do {
                words.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return words
    }
}
