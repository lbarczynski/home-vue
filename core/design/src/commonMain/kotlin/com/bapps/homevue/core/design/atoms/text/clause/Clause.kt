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

@Stable
sealed interface Clause

internal interface Parser<TClause> where TClause : Clause {
    suspend fun parse(clause: TClause): AnnotatedString
}

internal class ClauseParser(
    private val textClauseParser: Parser<TextClause> = TextClauseParser(),
    private val drawableClauseParser: Parser<DrawableClause> = DrawableClauseParser()
) : Parser<Clause> {
    override suspend fun parse(clause: Clause): AnnotatedString {
        return when (clause) {
            is TextClause -> textClauseParser.parse(clause)
            is DrawableClause -> drawableClauseParser.parse(clause)
            is CombinedClause -> clause.innerClauses
                .map { innerClause -> parse(innerClause) }
                .reduce(AnnotatedString::plus)
        }
    }
}

internal val LocalClauseParser = staticCompositionLocalOf { ClauseParser() }

@Composable
internal fun parse(clause: Clause): AnnotatedString {
    val clauseParser: Parser<Clause> = LocalClauseParser.current
    var parsed by remember { mutableStateOf(AnnotatedString("")) }
    LaunchedEffect(clause) {
        parsed = clauseParser.parse(clause)
    }
    return parsed
}