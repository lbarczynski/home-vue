package com.bapps.homevue.core.design.atoms.text.clause

import androidx.compose.ui.text.AnnotatedString

data class CombinedClause(
    val innerClauses: List<Clause>
) : Clause

internal class CombinedClauseParser(
    private val textClauseParser: Parser<TextClause> = TextClauseParser(),
    private val drawableClauseParser: Parser<DrawableClause> = DrawableClauseParser()
) : Parser<CombinedClause> {
    override fun parse(clause: CombinedClause): AnnotatedString {
        return clause.innerClauses
            .map { innerClause: Clause ->
                when(innerClause) {
                    is CombinedClause -> parse(innerClause)
                    is DrawableClause -> drawableClauseParser.parse(innerClause)
                    is TextClause -> textClauseParser.parse(innerClause)
                }
            }
            .reduce { acc, annotatedString -> acc + annotatedString }
    }
}

operator fun Clause.plus(clause: Clause): Clause {
    return when (this) {
        is CombinedClause -> CombinedClause(innerClauses + listOf(clause))
        else -> CombinedClause(innerClauses = listOf(this, clause))
    }
}