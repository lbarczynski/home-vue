package com.bapps.homevue.core.design.atoms.text.clause

import androidx.compose.ui.text.AnnotatedString
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TextClauseParserTest {

    private val textClauseParser: Parser<TextClause> = TextClauseParser()

    @Test
    fun `GIVEN plain text clause WHEN parse THEN should properly formatted annotated string`() {
        // given
        val plainTextClause: TextClause.Plain = textClause("test")

        // when
        val result = textClauseParser.parse(plainTextClause)

        // then
        val expectedResult = AnnotatedString("test")
        assertEquals(expectedResult, result)
    }
}