package com.bapps.homevue.core.design.atoms.text.clause

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.AnnotatedString
import com.bapps.homevue.core.design.atoms.text.clause.style.ClauseStyle
import com.bapps.homevue.core.design.atoms.text.clause.style.StyledClause
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

@Immutable
sealed class TextClause : Clause, StyledClause {
    @Immutable
    data class Plain(
        val text: String,
        override val style: ClauseStyle = ClauseStyle.Default
    ) : TextClause()

    @OptIn(ExperimentalResourceApi::class)
    @Immutable
    data class Localized(
        val resource: StringResource,
        override val style: ClauseStyle = ClauseStyle.Default
    ) : TextClause()

    companion object {
        val whitespace = Plain(text = " ")
    }
}

fun textClause(text: String, style: ClauseStyle = ClauseStyle.Default) =
    TextClause.Plain(text, style)

@OptIn(ExperimentalResourceApi::class)
fun localizedClause(resource: StringResource, style: ClauseStyle = ClauseStyle.Default) =
    TextClause.Localized(resource, style)

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
