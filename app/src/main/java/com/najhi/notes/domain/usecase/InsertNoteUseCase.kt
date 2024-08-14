package com.najhi.notes.domain.usecase

import com.najhi.notes.domain.model.Note
import com.najhi.notes.domain.repository.NoteRepository
import com.najhi.notes.domain.validation.BadNoteDataException

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 05/08/2024
 */
class InsertNoteUseCase(private val repository: NoteRepository)
{
    @Throws(BadNoteDataException::class)
    suspend fun insert(note: Note)
    {
        invoke(note)
    }

    @Throws(BadNoteDataException::class)
    private suspend fun invoke(obj: Any?)
    {
        val data = obj as Note
        if (data.title.isBlank() && data.content.isBlank())
        {
            throw BadNoteDataException("The data of the note can't be empty.")
        }
        repository.insert(data)
    }
}