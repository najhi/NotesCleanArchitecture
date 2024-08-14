package com.najhi.notes.domain.usecase

import androidx.lifecycle.LiveData
import com.najhi.notes.domain.model.Note
import com.najhi.notes.domain.repository.NoteRepository

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 05/08/2024
 */
class GetAllNotesUseCase(private val repository: NoteRepository)
{
    fun get(sortBy: String, orderBy: String): LiveData<List<Note>>
    {
        return invoke(sortBy, orderBy)
    }

    private fun invoke(sortBy: String, orderBy: String): LiveData<List<Note>>
    {
        return repository.getAllNotes(sortBy, orderBy)
    }
}