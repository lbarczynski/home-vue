package com.bapps.homevue.core.design.atoms.text.clause

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.times
import com.bapps.homevue.core.design.atoms.image.Drawable
import com.bapps.homevue.core.design.atoms.image.Image
import com.bapps.homevue.core.design.atoms.image.drawable

@Immutable
data class DrawableClause(
    val drawable: Drawable,
    val identifier: String = randomIdentifier(),
    val alternateText: TextClause = drawable.contentDescription ?: DefaultReplacementAlternateText
) : Clause

fun drawableClause(
    vector: ImageVector,
    contentDescription: TextClause? = null,
    style: Drawable.Style? = null
): DrawableClause {
    val drawable = drawable(
        vector = vector,
        contentDescription = contentDescription,
        style = style
    )
    return DrawableClause(drawable)
}

internal class DrawableClauseParser(
    private val textClauseParser : Parser<TextClause> = TextClauseParser()
) : Parser<DrawableClause> {
    override suspend fun parse(clause: DrawableClause): AnnotatedString {
        return buildAnnotatedString {
            appendInlineContent(
                id = clause.identifier,
                alternateText = textClauseParser.parse(clause.alternateText).text
            )
        }
    }
}

@Composable
internal fun DrawableClause.inlineTextContent(textHeight: TextUnit, color: Color): InlineTextContent {
    return InlineTextContent(
        placeholder = Placeholder(
            width = widthMultiplier(drawable) * textHeight,
            height = textHeight,
            placeholderVerticalAlign = PlaceholderVerticalAlign.Center
        ),
        children = {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                drawable = drawable,
                colorFilter = color
                    .takeIf { color.isSpecified && drawable.style == null }
                    ?.let { ColorFilter.tint(color) }
            )
        }
    )
}

@Composable
private fun widthMultiplier(drawable: Drawable): Float = when (drawable) {
    is Drawable.Vector -> drawable.vector.defaultWidth.value / drawable.vector.defaultHeight.value
}

private const val ID_LENGTH = 64
private val DefaultReplacementAlternateText = textClause("\uFFFD")
private val ID_CHARS = ('a'..'z') + ('0'..'9')
private fun randomIdentifier(): String =
    (1..ID_LENGTH).asSequence()
        .map { ID_CHARS.random() }
        .joinToString(separator = "")
