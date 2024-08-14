package com.najhi.notes.domain.repository

import androidx.lifecycle.LiveData
import com.najhi.notes.domain.model.Note

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 05/08/2024
 */
interface NoteRepository
{
    fun getAllNotes(sortBy: String, orderBy: String): LiveData<List<Note>>

    suspend fun insert(note: Note)

    suspend fun delete(notes: List<Note>)

    fun searchNotes(query: String): LiveData<List<Note>>
}