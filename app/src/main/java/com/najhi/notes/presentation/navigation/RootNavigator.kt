package com.najhi.notes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.najhi.notes.presentation.NoteApp
import com.najhi.notes.presentation.NoteCreationScreen
import com.najhi.notes.viewmodel.NoteViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 22/07/2024
 */

@Composable
fun RootNavigator()
{
    val navController = rememberNavController()
    val noteViewModel: NoteViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = Notes) {
        composable<Notes> {
            NoteApp(
                noteViewModel = noteViewModel,
                onCreateNotePressed = { navController.navigate(CreateNote) },
                onNoteSelected = {
                    noteViewModel.selectedNote = it
                    navController.navigate(CreateNote)
                })
        }
        composable<CreateNote> {
            NoteCreationScreen(noteViewModel) {
               navController.navigateUp()
            }
        }
    }
}