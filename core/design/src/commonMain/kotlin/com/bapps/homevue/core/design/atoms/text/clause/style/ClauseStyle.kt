package com.bapps.homevue.core.design.atoms.text.clause.style

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Immutable
data class ClauseStyle(
    val color: Color? = null,
    val fontWeight: FontWeight? = null,
    val isUnderlined: Boolean = false,
    val isStrikethrough: Boolean = false
) {
    companion object {
        val Default = ClauseStyle()
    }
}

interface StyledClause {
    val style: ClauseStyle
}