package dev.bapps.homevue

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.bapps.homevue.core.design.atoms.text.Text
import com.bapps.homevue.core.design.atoms.text.clause.TextClause
import com.bapps.homevue.core.design.atoms.text.clause.drawableClause
import com.bapps.homevue.core.design.atoms.text.clause.plus
import com.bapps.homevue.core.design.atoms.text.clause.textClause


@Composable
fun App() {
    Text(
        text = textClause("Hello,") +
                TextClause.whitespace +
                textClause("WORLD!") +
                TextClause.whitespace +
                drawableClause(Icons.Default.ThumbUp),
        color = Color.Red
    )
}