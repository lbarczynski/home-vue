package com.bapps.homevue.core.design.atoms.text

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.sp
import com.bapps.homevue.core.design.atoms.image.Drawable
import com.bapps.homevue.core.design.atoms.image.Image
import com.bapps.homevue.core.design.atoms.text.clause.Clause
import com.bapps.homevue.core.design.atoms.text.clause.CombinedClause
import com.bapps.homevue.core.design.atoms.text.clause.DrawableClause
import com.bapps.homevue.core.design.atoms.text.clause.LocalClauseParser
import com.bapps.homevue.core.design.atoms.text.clause.TextClause
import com.bapps.homevue.core.design.atoms.text.clause.inlineTextContent
import com.bapps.homevue.core.design.atoms.text.clause.parse

@Composable
fun Text(
    text: Clause,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    androidx.compose.material.Text(
        text = parse(clause = text),
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = text.inlineContent(fontSize, style, color),
        onTextLayout = onTextLayout,
        style = style
    )
}

@Composable
private fun Clause.inlineContent(
    fontSize: TextUnit,
    textStyle: TextStyle,
    textColor: Color
): Map<String, InlineTextContent> {

    val textHeight: TextUnit = when {
        fontSize.isSpecified -> fontSize
        textStyle.fontSize.isSpecified -> textStyle.fontSize
        LocalTextStyle.current.fontSize.isSpecified -> LocalTextStyle.current.fontSize
        else -> DefaultFontSize
    }

    return when (this) {
        is CombinedClause -> innerClauses
            .map { clause -> clause.inlineContent(fontSize, textStyle, textColor) }
            .reduce { acc, map -> acc + map }

        is DrawableClause -> mapOf(
            identifier to inlineTextContent(textHeight, textColor)
        )

        else -> emptyMap()
    }
}

// androidx.compose.ui.text.DefaultFontSize
private val DefaultFontSize = 14.sp