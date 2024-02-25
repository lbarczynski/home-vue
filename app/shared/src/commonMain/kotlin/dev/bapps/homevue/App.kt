package dev.bapps.homevue

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.bapps.homevue.core.design.atoms.image.Drawable
import com.bapps.homevue.core.design.atoms.text.Text
import com.bapps.homevue.core.design.atoms.text.clause.TextClause
import com.bapps.homevue.core.design.atoms.text.clause.drawableClause
import com.bapps.homevue.core.design.atoms.text.clause.plus
import com.bapps.homevue.core.design.atoms.text.clause.textClause
import dev.bapps.homevue.core.logger.DebugLogger
import dev.bapps.homevue.core.logger.Logger


@Composable
fun App() {
    Logger.debug("App composable")
    Text(
        text = textClause("Hello,") +
                TextClause.whitespace +
                textClause("WORLD!") +
                TextClause.whitespace +
                drawableClause(
                    vector = Icons.Default.ThumbUp,
                    contentDescription = textClause("Thumb up"),
                    style = Drawable.Style.Success
                ),
        color = Color.Red,
        fontSize = 36.sp
    )
}