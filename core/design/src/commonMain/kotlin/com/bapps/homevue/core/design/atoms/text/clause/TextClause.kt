package com.bapps.homevue.core.design.atoms.text.clause

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.AnnotatedString
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

@Immutable
sealed class TextClause : Clause {
    @Immutable
    data class Plain(
        val text: String
    ) : TextClause()

    @OptIn(ExperimentalResourceApi::class)
    @Immutable
    data class Localized(
        val resource: StringResource
    ) : TextClause()

    companion object {
        val whitespace = Plain(text = " ")
    }
}

fun textClause(text: String) = TextClause.Plain(text)
@OptIn(ExperimentalResourceApi::class)
fun localizedClause(resource: StringResource) = TextClause.Localized(resource)

internal class TextClauseParser : Parser<TextClause> {
    @OptIn(ExperimentalResourceApi::class)
    override suspend fun parse(clause: TextClause): AnnotatedString {
        return when (clause) {
            is TextClause.Plain -> parse(text = clause.text)
            is TextClause.Localized -> parse(text = getString(clause.resource))
        }
    }

    private fun parse(text: String): AnnotatedString {
        return AnnotatedString(text = text)
    }
}
