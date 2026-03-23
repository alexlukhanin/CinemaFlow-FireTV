package solutions.sgbrightkit.cinemaflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    listOf("SPACE", "⌫")
)

/**
 * A D-pad-friendly on-screen keyboard for Fire TV / Android TV.
 *
 * @param query       Current typed text – displayed in the search bar above the keys.
 * @param onKeyPress  Called with the character that was pressed (e.g. "A", " ", or "" for backspace).
 */
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TvKeyboard(
    query: String,
    onKeyPress: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        // ── Search bar display ────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = if (query.isEmpty()) "🔍  Start typing…" else "🔍  $query",
                style = MaterialTheme.typography.bodyLarge,
                color = if (query.isEmpty())
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                else
                    MaterialTheme.colorScheme.onSurface
            )
        }

        // ── Key rows ──────────────────────────────────────────────────────
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
                            "SPACE" -> 120.dp
                            "⌫"    -> 64.dp
                            else   -> 38.dp
                        },
                        isSpecial = key == "⌫" || key == "SPACE",
                        onClick = {
                            when (key) {
                                "SPACE" -> onKeyPress(" ")
                                "⌫"    -> onKeyPress("")      // empty string = backspace signal
                                else   -> onKeyPress(key)
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
    isSpecial: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(2.dp)
            .width(width)
            .height(36.dp),
        colors = ButtonDefaults.colors(
            containerColor = if (isSpecial)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.primary
        ),
        contentPadding = PaddingValues(0.dp),
        scale = ButtonDefaults.scale(focusedScale = 1.08f)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}
