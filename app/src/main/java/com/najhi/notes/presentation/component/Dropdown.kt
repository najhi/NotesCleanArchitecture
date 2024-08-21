package com.najhi.notes.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 13/08/2024
 */

@Composable
fun Dropdown(
    modifier: Modifier = Modifier,
    items: List<String>,
    selected: Int = 0,
    onSelectionChanged: (Int) -> Unit
)
{
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableIntStateOf(selected) }

    Box(modifier = modifier) {
        Row(modifier = Modifier.clickable(onClick = { expanded = true }), horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.List, contentDescription = "Order", tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(items[selectedIndex], color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    onSelectionChanged(selectedIndex)
                }, text = {
                    Text(text = s, color = MaterialTheme.colorScheme.onSurfaceVariant)
                })
            }
        }
    }
}