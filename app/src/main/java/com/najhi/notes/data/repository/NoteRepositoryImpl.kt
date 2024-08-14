package com.najhi.notes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.najhi.notes.data.datastore.NoteDao
import com.najhi.notes.domain.model.Note
import com.najhi.notes.domain.repository.NoteRepository

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 21/07/2024
 */

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository
{
    override fun getAllNotes(sortBy: String, orderBy: String): LiveData<List<Note>>
    {
        return noteDao.getAllNotes(sortBy, orderBy)
    }

    override suspend fun insert(note: Note)
    {
        if (note.id == 0)
        {
            noteDao.insert(note)
        } else
        {
            noteDao.update(note)
        }
    }

    override suspend fun delete(notes: List<Note>)
    {
        noteDao.delete(notes)
    }

    override fun searchNotes(query: String): LiveData<List<Note>>
    {
        return if (query.isBlank()) MutableLiveData(emptyList()) else noteDao.searchNotes("%$query%")
    }
}