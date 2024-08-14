package com.najhi.notes.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.najhi.notes.domain.model.Note
import com.najhi.notes.viewmodel.NoteViewModel

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 22/07/2024
 */

@Composable
fun NoteCreationScreen(noteViewModel: NoteViewModel, onNoteSaved: () -> Unit)
{
    val _id = noteViewModel.selectedNote?.id ?: 0
    val initialTitle = noteViewModel.selectedNote?.title ?: ""
    val initialContent = noteViewModel.selectedNote?.content ?: ""
    var title by remember { mutableStateOf(initialTitle) }
    var content by remember { mutableStateOf(initialContent) }

    BackHandler {
        val note = Note(
            id = _id,
            title = title,
            content = content,
            tags = "",
            created = noteViewModel.selectedNote?.created ?: System.currentTimeMillis(),
            modified = System.currentTimeMillis()
        )

        if ((title != initialTitle || content != initialContent))
        {
            noteViewModel.insert(note)
        }

        noteViewModel.selectedNote = null
        onNoteSaved()
    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(16.dp)
            .fillMaxSize()
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f),
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}