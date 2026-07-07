package com.projectpulse.mydialer.ui.screens.dialpad.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DialPadGrid(
    onNumberClick: (String) -> Unit,
    onLongClickZero: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keys = listOf(
        listOf(KeyInfo("1", null), KeyInfo("2", "ABC"), KeyInfo("3", "DEF")),
        listOf(KeyInfo("4", "GHI"), KeyInfo("5", "JKL"), KeyInfo("6", "MNO")),
        listOf(KeyInfo("7", "PQRS"), KeyInfo("8", "TUV"), KeyInfo("9", "WXYZ")),
        listOf(KeyInfo("*", null), KeyInfo("0", "+"), KeyInfo("#", null))
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp), // Reduced spacing
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { key ->
                    DialPadButton(
                        number = key.number,
                        letters = key.letters,
                        onClick = { onNumberClick(key.number) },
                        onLongClick = if (key.number == "0") onLongClickZero else null
                    )
                }
            }
        }
    }
}

private data class KeyInfo(val number: String, val letters: String?)
