package com.najhi.notes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najhi.notes.data.datastore.PreferencesManager
import com.najhi.notes.domain.model.Note
import com.najhi.notes.domain.usecase.NoteUseCases
import com.najhi.notes.presentation.NoteSavedState
import com.najhi.notes.presentation.OrderBy
import com.najhi.notes.presentation.SortBy
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 21/07/2024
 */

class NoteViewModel(private val noteUseCases: NoteUseCases) : ViewModel(), KoinComponent
{
    private val preferences: PreferencesManager by inject()

    val sortItems = SortBy.entries.map { it.displayName }
    val orderItems = OrderBy.entries.map { it.name }

    private val _selectedSortBy = MutableLiveData(preferences.getData("sortby", sortItems[0]))
    val selectedSortBy: LiveData<String> get() = _selectedSortBy

    private val _selectedOrderBy = MutableLiveData(preferences.getData("orderby", orderItems[0]))
    val selectedOrderBy: LiveData<String> get() = _selectedOrderBy

    val allNotes: LiveData<List<Note>> get() = _allNotes
    private val _allNotes = MediatorLiveData<List<Note>>(emptyList()).apply {
        addSource(_selectedSortBy) { sortBy ->
            updateNotesSource(sortBy, _selectedOrderBy.value ?: orderItems[0])
        }
        addSource(_selectedOrderBy) { orderBy ->
            updateNotesSource(_selectedSortBy.value ?: sortItems[0], orderBy)
        }
    }

    private val _searchResults = MediatorLiveData<List<Note>>()
    val searchResults: LiveData<List<Note>> get() = _searchResults

    var selectedNote: Note? = null

    var savedState by mutableStateOf(NoteSavedState(false))
        private set

    private fun updateNotesSource(sortBy: String, orderBy: String) {
        val newSource = noteUseCases.getAllNotesUseCase.get(sortBy, orderBy)
        _allNotes.addSource(newSource) { notes ->
            _allNotes.value = notes
        }
    }

    fun updateSortBy(value: String) {
        _selectedSortBy.value = value
        preferences.saveData("sortby", value)
    }

    fun updateOrderBy(value: String) {
        _selectedOrderBy.value = value
        preferences.saveData("orderby", value)
    }

    fun insert(note: Note)
    {
        val exceptionHandler = CoroutineExceptionHandler { _, error ->
            savedState = savedState.copy(isSaved = false, message = error.localizedMessage)
        }

        viewModelScope.launch(exceptionHandler) {
            noteUseCases.insertNoteUseCase.insert(note)
            savedState = savedState.copy(isSaved = true, message = "Note saved")
            updateNotesSource(_selectedSortBy.value ?: sortItems[0], _selectedOrderBy.value ?: orderItems[0])
        }
    }

    fun resetSavedState()
    {
        savedState = savedState.copy(isSaved = false, message = null)
    }

    fun delete(notes: List<Note>) = viewModelScope.launch {
        noteUseCases.deleteNoteUseCase.delete(notes)
        updateNotesSource(_selectedSortBy.value ?: sortItems[0], _selectedOrderBy.value ?: orderItems[0])
    }

    fun searchNotes(query: String) = viewModelScope.launch {
        val searchLiveData = noteUseCases.searchNoteUseCase.search(query)
        _searchResults.addSource(searchLiveData) { searchResults ->
            _searchResults.value = searchResults
        }
    }
}