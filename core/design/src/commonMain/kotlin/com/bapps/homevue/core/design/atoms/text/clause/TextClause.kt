package com.bapps.homevue.core.design.atoms.text.clause

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.AnnotatedString

@Immutable
sealed class TextClause : Clause {
    @Immutable
    data class Plain(
        val text: String
    ) : TextClause()

    companion object {
        val whitespace = Plain(text = " ")
    }
}

fun textClause(text: String): TextClause =
    TextClause.Plain(text)

internal class TextClauseParser : Parser<TextClause> {
    override fun parse(clause: TextClause): AnnotatedString {
        return when (clause) {
            is TextClause.Plain -> parse(clause)
        }
    }

    private fun parse(clause: TextClause.Plain): AnnotatedString {
        return AnnotatedString(text = clause.text)
    }
}
