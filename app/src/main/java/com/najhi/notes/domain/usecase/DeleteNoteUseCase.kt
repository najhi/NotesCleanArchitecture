package com.najhi.notes.domain.usecase

import com.najhi.notes.domain.model.Note
import com.najhi.notes.domain.repository.NoteRepository

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 05/08/2024
 */
class DeleteNoteUseCase(private val repository: NoteRepository)
{
    suspend fun delete(notes: List<Note>)
    {
        invoke(notes)
    }

    private suspend fun invoke(obj: Any?)
    {
        if (obj is List<*>)
        {
            val data : List<Note> = obj.filterIsInstance<Note>()
            repository.delete(data)
        }
    }
}