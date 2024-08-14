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
class SearchNoteUseCase(private val repository: NoteRepository)
{
    fun search(query: String) : LiveData<List<Note>>
    {
        return invoke(query)
    }

    fun invoke(query: String): LiveData<List<Note>>
    {
        return repository.searchNotes(query)
    }
}