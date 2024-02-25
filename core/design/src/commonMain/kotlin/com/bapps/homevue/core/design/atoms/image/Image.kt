package com.bapps.homevue.core.design.atoms.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import com.bapps.homevue.core.design.atoms.text.clause.parse

@Composable
fun Image(
    drawable: Drawable,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    androidx.compose.foundation.Image(
        modifier = modifier,
        painter = when (drawable) {
            is Drawable.Vector -> rememberVectorPainter(drawable.vector)
        },
        contentDescription = drawable.contentDescription
            ?.let { clause -> parse(clause) }
            ?.text,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter ?: drawable.style?.let { colorFilter(it) }
    )
}

@Composable
private fun colorFilter(style: Drawable.Style) : ColorFilter {
    return ColorFilter.tint(
        // TODO: replace with theme values
        color = when(style) {
            Drawable.Style.Success -> Color.Green
            Drawable.Style.Warning -> Color.Yellow
            Drawable.Style.Error -> Color.Red
        }
    )
}
