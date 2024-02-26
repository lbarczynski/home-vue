package com.bapps.homevue.core.design.atoms.text.clause

import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import com.bapps.homevue.core.design.atoms.image.Drawable
import com.bapps.homevue.core.design.atoms.text.clause.mock.mockImageVector
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DrawableClauseParserTest {

    private val textClauseParser: Parser<TextClause> = mock()
    private val drawableClauseParser: Parser<DrawableClause> = DrawableClauseParser(textClauseParser)

    @Test
    fun `GIVEN drawable clause WHEN parse THEN should return properly formatted annotated string`() {
        // given
        val imageVector: ImageVector = mockImageVector()
        val imageContentDescription = textClause("contentDescription")
        val identifier = "test_clause"
        val alternateText = textClause("alternateText")
        val drawableClause = DrawableClause(
            drawable = Drawable.Vector(
                vector = imageVector,
                contentDescription = imageContentDescription,
                style = Drawable.Style.Success
            ),
            identifier = identifier,
            alternateText = alternateText
        )
        every { textClauseParser.parse(alternateText) } returns AnnotatedString("alternateText")

        // when
        val result = drawableClauseParser.parse(drawableClause)

        // then
        val expectedResult = buildAnnotatedString {
            appendInlineContent(id = "test_clause", alternateText = "alternateText")
        }
        assertEquals(expectedResult, result)
    }
}