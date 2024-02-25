package com.bapps.homevue.core.design.atoms.image

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.bapps.homevue.core.design.atoms.text.clause.Clause
import com.bapps.homevue.core.design.atoms.text.clause.TextClause

@Immutable
sealed class Drawable {

    abstract val contentDescription: TextClause?
    abstract val style: Style?

    @Immutable
    data class Vector(
        val vector: ImageVector,
        override val contentDescription: TextClause? = null,
        override val style: Style? = null
    ) : Drawable()


    sealed class Style {
        data object Success : Style()
        data object Warning : Style()
        data object Error : Style()
    }
}

fun drawable(
    vector: ImageVector,
    contentDescription: TextClause? = null,
    style: Drawable.Style? = null
): Drawable.Vector {
    return Drawable.Vector(
        vector = vector,
        contentDescription = contentDescription,
        style = style
    )
}