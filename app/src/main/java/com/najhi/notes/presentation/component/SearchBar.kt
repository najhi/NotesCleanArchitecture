package com.najhi.notes.presentation.component

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 22/07/2024
 */

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBar(
    onQueryChange: ((String) -> Unit)? = null,
    isSearchActive: Boolean,
    onActiveChanged: (Boolean) -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onSearch: ((String) -> Unit)? = null,
    content: @Composable (() -> Unit)? = null
)
{
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val activeChanged: (Boolean) -> Unit = { active ->
        if(active)
        {
            searchQuery = ""
            onQueryChange?.let { it("") }
        }
        onActiveChanged(active)
    }
    SearchBar(
        query = searchQuery,
        onQueryChange = { query ->
            searchQuery = query
            onQueryChange?.let { it(query) }
        },
        onSearch = { search ->
            onSearch?.let { it(search) }
        },
        active = isSearchActive,
        onActiveChange = activeChanged,
        modifier = if (isSearchActive)
        {
            modifier.animateContentSize(spring(stiffness = Spring.StiffnessHigh))
        } else
        {
            modifier.padding(start = 12.dp, top = 2.dp, end = 12.dp, bottom = 12.dp).fillMaxWidth().animateContentSize(spring(stiffness = Spring.StiffnessHigh))
        },
        placeholder = { Text("Search") },
        leadingIcon = {
            if (isSearchActive)
            {
                IconButton(
                    onClick = { activeChanged(false) },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            } else
            {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        trailingIcon = if (isSearchActive && searchQuery.isNotEmpty())
        {
            {
                IconButton(
                    onClick = {
                        searchQuery = ""
                        onQueryChange?.let { it("") }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Clear",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        } else
        {
            null
        },
        tonalElevation = 0.dp,
        colors = SearchBarDefaults.colors(
            containerColor = if (isSearchActive)
            {
                MaterialTheme.colorScheme.background
            } else
            {
                MaterialTheme.colorScheme.surfaceContainerLow
            },
        ),
        windowInsets = WindowInsets(0.dp),
    ) {
        content?.invoke()
    }
}