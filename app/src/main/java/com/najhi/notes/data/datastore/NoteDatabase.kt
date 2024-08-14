package com.najhi.notes.data.datastore

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.najhi.notes.domain.model.Note

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 21/07/2024
 */

@Database(entities = [Note::class], version = 2)
abstract class NoteDatabase : RoomDatabase()
{
    abstract fun noteDao(): NoteDao

    companion object
    {
        const val DATABASE_NAME = "note_database"
    }
}

val MIGRATION_1_2 = Migration(1, 2) { database ->
    database.execSQL("ALTER TABLE notes ADD COLUMN modified INTEGER NOT NULL DEFAULT 0")
    database.execSQL("ALTER TABLE notes ADD COLUMN starred INTEGER NOT NULL DEFAULT 0")
}
