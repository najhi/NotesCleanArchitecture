package com.najhi.notes.di

import androidx.room.Room
import com.najhi.notes.data.datastore.MIGRATION_1_2
import com.najhi.notes.data.datastore.NoteDatabase
import com.najhi.notes.data.datastore.PreferencesManager
import com.najhi.notes.data.repository.NoteRepositoryImpl
import com.najhi.notes.domain.repository.NoteRepository
import com.najhi.notes.domain.usecase.DeleteNoteUseCase
import com.najhi.notes.domain.usecase.GetAllNotesUseCase
import com.najhi.notes.domain.usecase.InsertNoteUseCase
import com.najhi.notes.domain.usecase.NoteUseCases
import com.najhi.notes.domain.usecase.SearchNoteUseCase
import com.najhi.notes.viewmodel.NoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 05/08/2024
 */

val appModule = module {

    viewModelOf(::NoteViewModel)

    single {
        NoteUseCases(
            getAllNotesUseCase = GetAllNotesUseCase(get()),
            insertNoteUseCase = InsertNoteUseCase(get()),
            deleteNoteUseCase = DeleteNoteUseCase(get()),
            searchNoteUseCase = SearchNoteUseCase(get())
        )
    }
}
