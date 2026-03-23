package solutions.sgbrightkit.cinemaflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

private val KEY_ROWS = listOf(
    listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
    listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
    listOf("Z", "X", "C", "V", "B", "N", "M"),
    listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
    listOf("SPACE", "⌫", "SEARCH")
)

/**
 * D-pad-friendly on-screen keyboard for Fire TV / Android TV.
 * No internal search bar – the caller owns the query display.
 *
 * @param onKeyPress   Called with the pressed character ("A", " ", "" for backspace).
 * @param onSearch     Called when the user presses the SEARCH key.
 */
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TvKeyboard(
    onKeyPress: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 10.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        KEY_ROWS.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { key ->
                    KeyButton(
                        label = key,
                        width = when (key) {
                            "SEARCH" -> 160.dp
                            "SPACE"  -> 220.dp
                            "⌫"      -> 100.dp
                            else     -> 70.dp
                        },
                        isAction = key == "SEARCH",
                        isSpecial = key == "⌫" || key == "SPACE",
                        onClick = {
                            when (key) {
                                "SEARCH" -> onSearch()
                                "SPACE"  -> onKeyPress(" ")
                                "⌫"      -> onKeyPress("")   // empty = backspace
                                else     -> onKeyPress(key)
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun KeyButton(
    label: String,
    width: Dp,
    isAction: Boolean,
    isSpecial: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .width(width)
            .height(70.dp),
        colors = ButtonDefaults.colors(
            containerColor = when {
                isAction  -> MaterialTheme.colorScheme.primary
                isSpecial -> MaterialTheme.colorScheme.secondary
                else      -> MaterialTheme.colorScheme.primaryContainer
            },
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            focusedContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
