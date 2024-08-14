package com.najhi.notes.domain.usecase

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 05/08/2024
 */
data class NoteUseCases(
    val getAllNotesUseCase: GetAllNotesUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val searchNoteUseCase: SearchNoteUseCase
)