package com.najhi.notes.di

import androidx.room.Room
import com.najhi.notes.data.datastore.MIGRATION_1_2
import com.najhi.notes.data.datastore.NoteDatabase
import com.najhi.notes.data.datastore.PreferencesManager
import org.koin.dsl.module

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 14/08/2024
 */

val dataStoreModule = module {
    single { PreferencesManager(get()) }
    single {
        Room.databaseBuilder(
            get(), NoteDatabase::class.java, NoteDatabase.DATABASE_NAME
        ).addMigrations(MIGRATION_1_2).build()
    }
}