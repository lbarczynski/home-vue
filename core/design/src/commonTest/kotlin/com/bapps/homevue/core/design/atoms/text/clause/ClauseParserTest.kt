package com.bapps.homevue.core.design.atoms.text.clause

import kotlin.test.BeforeTest

internal class ClauseParserTest {

    private lateinit var clauseParser: Parser<Clause>

    @BeforeTest
    fun setup() {
        clauseParser = ClauseParser()
    }
}