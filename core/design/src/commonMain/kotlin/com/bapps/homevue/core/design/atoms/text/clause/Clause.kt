package com.bapps.homevue.core.design.atoms.text.clause

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.AnnotatedString
import com.bapps.homevue.core.design.atoms.text.clause.style.AnnotatedStringClauseStyleDecorator
import com.bapps.homevue.core.design.atoms.text.clause.style.ClauseStyle
import com.bapps.homevue.core.design.atoms.text.clause.style.ClauseStyleDecorator
import com.bapps.homevue.core.design.atoms.text.clause.style.LocalClauseStyleDecorator
import com.bapps.homevue.core.design.atoms.text.clause.style.StyledClause

@Stable
sealed interface Clause

internal interface Parser<TClause> where TClause : Clause {
    suspend fun parse(clause: TClause): AnnotatedString
}

internal class ClauseParser(
    private val textClauseParser: Parser<TextClause> = TextClauseParser(),
    private val drawableClauseParser: Parser<DrawableClause> = DrawableClauseParser(),
    private val decorator: ClauseStyleDecorator = AnnotatedStringClauseStyleDecorator()
) : Parser<Clause> {
    override suspend fun parse(clause: Clause): AnnotatedString {
        return when (clause) {
            is TextClause -> textClauseParser.parse(clause)
            is DrawableClause -> drawableClauseParser.parse(clause)
            is CombinedClause -> clause.innerClauses
                .map { innerClause -> parse(innerClause) }
                .reduce(AnnotatedString::plus)
        }.decorate(clause.style)
    }

    private val Clause.style : ClauseStyle?
        get() = if(this is StyledClause) this.style else null

    private fun AnnotatedString.decorate(style: ClauseStyle?) : AnnotatedString {
        return style?.let { decorator.decorate(this, it) } ?: this
    }
}

internal val LocalClauseParser = staticCompositionLocalOf { ClauseParser() }

@Composable
internal fun parse(clause: Clause): AnnotatedString {
    val clauseParser: Parser<Clause> = LocalClauseParser.current
    var parsed: AnnotatedString by remember { mutableStateOf(AnnotatedString("")) }
    LaunchedEffect(clause) { parsed = clauseParser.parse(clause) }
    return parsed
}