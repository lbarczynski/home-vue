package com.bapps.homevue.core.design.atoms.text.clause

data class CombinedClause(
    val innerClauses: List<Clause>
) : Clause

operator fun Clause.plus(clause: Clause): Clause {
    return when (this) {
        is CombinedClause -> CombinedClause(innerClauses + listOf(clause))
        else -> CombinedClause(innerClauses = listOf(this, clause))
    }
}