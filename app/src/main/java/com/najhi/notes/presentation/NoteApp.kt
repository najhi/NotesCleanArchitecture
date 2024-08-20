package com.najhi.notes.presentation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.najhi.notes.domain.model.Note
import com.najhi.notes.presentation.component.Dropdown
import com.najhi.notes.presentation.component.SearchBar
import com.najhi.notes.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 21/07/2024
 */

private fun dismissEditMode(notes: List<Note>? = null, appState: MutableState<AppState>)
{
    if (appState.value == AppState.SEARCH_SELECT)
    {
        appState.value = AppState.SEARCH
    } else
    {
        appState.value = AppState.DEFAULT
    }
    notes?.filter { it.isSelected.value }?.forEach {
        it.isSelected.value = false
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteApp(noteViewModel: NoteViewModel, onCreateNotePressed: () -> Unit, onNoteSelected: (Note) -> Unit)
{
    val notes by noteViewModel.allNotes.observeAsState(initial = emptyList())
    val searchResults by noteViewModel.searchResults.observeAsState(initial = emptyList())
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val appState = rememberSaveable { mutableStateOf(AppState.DEFAULT) }

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var isOrderDescending by remember { mutableStateOf( noteViewModel.selectedOrderBy.value == OrderBy.DESCENDING.name) }

    val isInSelectMode by remember { derivedStateOf { appState.value == AppState.SELECT || appState.value == AppState.SEARCH_SELECT } }
    val isInSearchMode by remember { derivedStateOf { appState.value == AppState.SEARCH || appState.value == AppState.SEARCH_SELECT } }

    LaunchedEffect(key1 = noteViewModel.savedState) {
        if (!noteViewModel.savedState.message.isNullOrEmpty())
        {
            noteViewModel.savedState.message?.let {
                scope.launch {
                    snackBarHostState.showSnackbar(it)
                    noteViewModel.resetSavedState()
                }
            }
        }
    }

    BackHandler(enabled = isInSelectMode, onBack = {
        dismissEditMode(notes, appState)
    })

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            val appBarColor = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Column {
                AnimatedVisibility(visible = !isInSearchMode) {
                    LargeTopAppBar(
                        title = {
                            Text("All Notes", maxLines = 1, overflow = TextOverflow.Ellipsis)
                        },
                        actions = {
                            if (!isInSelectMode)
                            {
                                IconButton(
                                    onClick = {
                                        noteViewModel.searchNotes("")
                                        appState.value = AppState.SEARCH
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "Localized description"
                                    )
                                }
                            }
                        },
                        colors = appBarColor,
                        scrollBehavior = scrollBehavior,
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateNotePressed) {
                Icon(Icons.Filled.Add, contentDescription = "Add Note")
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isInSelectMode,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {

                    IconButton(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        onClick = {
                            noteViewModel.delete(notes.filter { it.isSelected.value })
                            dismissEditMode(appState = appState)
                        },
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        content = { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedVisibility(
                    visible = isInSearchMode, modifier = Modifier.statusBarsPadding()
                ) {
                    SearchBar(
                        onQueryChange = {
                            noteViewModel.searchNotes(it)
                        },
                        isSearchActive = isInSearchMode,
                        onActiveChanged = {
                            appState.value = if (it || isInSelectMode) AppState.SEARCH else AppState.DEFAULT
                        },
                    ) {
                        NoteList(notes = searchResults, appState = appState) {
                            onNoteSelected(it)
                        }
                    }
                }
                AnimatedVisibility(
                    visible = !isInSearchMode,
                    exit = slideOutVertically { it } + fadeOut(),
                    enter = slideInVertically { it } + fadeIn()
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "${notes.size} Notes")
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val sortItems = noteViewModel.sortItems
                                val orderItems = noteViewModel.orderItems
                                val selected = sortItems.indexOf(noteViewModel.selectedSortBy.value)
                                Dropdown(items = sortItems, selected = selected) { index ->
                                    noteViewModel.updateSortBy(sortItems[index])
                                }
                                Text(text = "〡", color = Color.Gray)

                                val orderIcon =
                                    if (isOrderDescending) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp
                                Icon(orderIcon,
                                    contentDescription = "Order",
                                    tint = Color.Gray,
                                    modifier = Modifier.clickable {
                                            isOrderDescending = !isOrderDescending
                                            noteViewModel.updateOrderBy(orderItems.find { it != noteViewModel.selectedOrderBy.value }!!)
                                        })
                            }
                        }
                        NoteList(notes = notes, appState = appState) {
                            onNoteSelected(it)
                        }
                    }
                }
            }
        },
    )
}
