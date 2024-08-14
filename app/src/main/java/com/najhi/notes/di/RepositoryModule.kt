package com.najhi.notes.di

import com.najhi.notes.data.datastore.NoteDatabase
import com.najhi.notes.data.repository.NoteRepositoryImpl
import com.najhi.notes.domain.repository.NoteRepository
import org.koin.dsl.module

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 05/08/2024
 */

val repositoryModule = module {
    single<NoteRepository> { NoteRepositoryImpl(get<NoteDatabase>().noteDao()) }
}
