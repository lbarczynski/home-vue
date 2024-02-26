package com.bapps.homevue.core.design.atoms.text.clause

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.AnnotatedString

@Stable
sealed interface Clause

internal interface Parser<TClause> where TClause : Clause {
    fun parse(clause: TClause): AnnotatedString
}

internal class ClauseParser(
    private val textClauseParser: Parser<TextClause> = TextClauseParser(),
    private val drawableClauseParser: Parser<DrawableClause> = DrawableClauseParser()
) : Parser<Clause> {
    override fun parse(clause: Clause): AnnotatedString {
        return when (clause) {
            is TextClause -> textClauseParser.parse(clause)
            is DrawableClause -> drawableClauseParser.parse(clause)
            is CombinedClause -> clause.innerClauses
                .map(::parse)
                .reduce(AnnotatedString::plus)
        }
    }
}

internal val LocalClauseParser = staticCompositionLocalOf { ClauseParser() }

@Composable
internal fun parse(clause: Clause): AnnotatedString {
    return LocalClauseParser.current.parse(clause)
}