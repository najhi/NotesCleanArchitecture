package com.najhi.notes.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.najhi.notes.domain.model.Note

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 12/08/2024
 */

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    appState: MutableState<AppState>,
    notes: List<Note>,
    onNoteSelected: (Note) -> Unit = {}
)
{
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(notes, key = { it.id }) { note ->
            NoteItem(note = note, appState) {
                onNoteSelected(it)
            }
        }
    }
}