package com.najhi.notes.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.najhi.notes.domain.model.Note
import com.najhi.notes.presentation.util.Utils

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 22/07/2024
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(note: Note, appState: MutableState<AppState>, onNoteClick: (Note) -> Unit)
{
    val isInSelectMode = appState.value == AppState.SELECT || appState.value == AppState.SEARCH_SELECT
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .combinedClickable(onClick = {
                    if (isInSelectMode)
                    {
                        note.isSelected.value = !note.isSelected.value
                    } else
                    {
                        onNoteClick(note)
                    }
                }, onLongClick = {
                    if(isInSelectMode)
                    {
                        return@combinedClickable
                    }

                    note.isSelected.value = true
                    if (appState.value == AppState.SEARCH)
                    {
                        appState.value = AppState.SEARCH_SELECT
                    } else
                    {
                        appState.value = AppState.SELECT
                    }
                }), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (isInSelectMode)
                {
                    Checkbox(checked = note.isSelected.value,
                        onCheckedChange = { note.isSelected.value = !note.isSelected.value })
                }
            }
        }

        val title = note.title.ifBlank { "Text note" }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = Utils.formatTimeStamp(note.created), style = MaterialTheme.typography.labelSmall)
    }
}