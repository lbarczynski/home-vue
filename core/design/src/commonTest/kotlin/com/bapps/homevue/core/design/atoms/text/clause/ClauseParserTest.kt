package com.bapps.homevue.core.design.atoms.text.clause

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.bapps.homevue.core.design.atoms.text.clause.mock.mockImageVector
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ClauseParserTest {

    private val textClauseParser: Parser<TextClause> = mock()
    private val drawableClauseParser: Parser<DrawableClause> = mock()
    private val clauseParser: Parser<Clause> = ClauseParser(
        textClauseParser = textClauseParser,
        drawableClauseParser = drawableClauseParser
    )

    @Test
    fun `GIVEN text clause WHEN parse THEN should execute text clause parser`() {
        // given
        val clause = textClause("test")
        val expectedResult = AnnotatedString("result")
        every { textClauseParser.parse(clause) } returns expectedResult

        // when
        val result = clauseParser.parse(clause)

        // then
        assertEquals(expectedResult, result)
        verify { textClauseParser.parse(clause) }
    }

    @Test
    fun `GIVEN combined clause WHEN parse THEN should process proper parsers`() {
        // given
        val imageVector: ImageVector = mockImageVector()
        val textClause = textClause("test")
        val drawableClause = drawableClause(vector = imageVector, contentDescription = textClause("image"))
        val clause = textClause + TextClause.whitespace + drawableClause

        every { textClauseParser.parse(textClause) } returns AnnotatedString("first")
        every { textClauseParser.parse(TextClause.whitespace) } returns AnnotatedString("+space+")
        every { drawableClauseParser.parse(drawableClause) } returns AnnotatedString("image")

        // when
        val result = clauseParser.parse(clause)

        // then
        val expectedResult = AnnotatedString("first+space+image")
        assertEquals(expectedResult, result)
        verify { textClauseParser.parse(textClause) }
        verify { textClauseParser.parse(TextClause.whitespace) }
        verify { drawableClauseParser.parse(drawableClause) }
    }
}