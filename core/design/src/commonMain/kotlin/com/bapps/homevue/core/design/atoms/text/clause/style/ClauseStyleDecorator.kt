package com.bapps.homevue.core.design.atoms.text.clause.style

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

internal interface ClauseStyleDecorator {
    fun decorate(text: AnnotatedString, style: ClauseStyle): AnnotatedString
}

internal val LocalClauseStyleDecorator = staticCompositionLocalOf { AnnotatedStringClauseStyleDecorator() }

internal class AnnotatedStringClauseStyleDecorator : ClauseStyleDecorator {
    override fun decorate(text: AnnotatedString, style: ClauseStyle): AnnotatedString {
        return buildAnnotatedString {
            withStyle(style = style.toSpanStyle()) {
                append(text)
            }
        }
    }

    private fun ClauseStyle.toSpanStyle(): SpanStyle {
        return SpanStyle(
            fontWeight = this.fontWeight,
            color = this.color ?: Color.Unspecified,
            textDecoration = this.textDecorations()
        )
    }

    private fun ClauseStyle.textDecorations(): TextDecoration {
        val decorations = buildList {
            if (isUnderlined) add(TextDecoration.Underline)
            if (isStrikethrough) add(TextDecoration.LineThrough)
        }
        return TextDecoration.combine(decorations)
    }
}