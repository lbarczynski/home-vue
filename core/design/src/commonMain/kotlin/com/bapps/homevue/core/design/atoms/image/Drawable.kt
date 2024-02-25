package com.bapps.homevue.core.design.atoms.image

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
sealed class Drawable {

    abstract val contentDescription: String?

    @Immutable
    data class Vector(
        val vector: ImageVector,
        override val contentDescription: String? = null
    ) : Drawable()
}

fun drawable(
    vector: ImageVector,
    contentDescription: String? = null
): Drawable.Vector = Drawable.Vector(vector, contentDescription)