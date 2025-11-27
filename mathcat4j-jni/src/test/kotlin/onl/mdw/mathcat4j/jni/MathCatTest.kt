/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
package onl.mdw.mathcat4j.jni

import onl.mdw.mathcat4j.api.NavigationId
import onl.mdw.mathcat4j.api.NavigationNode
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

private val mathCat = MathCatTransactional()

const val BASIC_MATHML = "<math id=\"n1\"><mrow id=\"n2\"><mi id=\"n3\">y</mi><mo id=\"n4\">=</mo><mrow id=\"r0\"><mi id=\"n5\">x</mi><mo id=\"n6\">+</mo><mn id=\"n7\">2</mn></mrow></mrow></math>"

class MathCatTest {
    @BeforeTest
    fun configureRules() {
        mathCat.run { it.setRulesDir(System.getProperty("onl.mdw.mathcat4j.testRulesDir")) }
    }
    @Test
    fun testGetBrailleForId() {
        val expected = "⠽⠀⣨⣅⠀⠭⠬⠆"
        mathCat.run {
            it.setMathml(BASIC_MATHML)
            assertEquals(expected, it.getBraille("n4"))
        }
    }
    @Test
    fun testGetSpokenText() {
        val expected = "y is equal to x plus 2"
        val actual = mathCat.run {
            it.setMathml(BASIC_MATHML)
            it.getSpokenText()
        }
        assertEquals(expected, actual)
    }
    @Test
    fun testGetOverviewText() {
        val expected = "y is equal to x plus 2"
        val actual = mathCat.run {
            it.setMathml(BASIC_MATHML)
            it.getOverviewText()
        }
        assertEquals(expected, actual)
    }
    @Test
    fun testDoNavigateKeypress() {
        val expected = "end of math"
        val actual = mathCat.run {
            it.setMathml(BASIC_MATHML)
            it.doNavigateKeypress(39, false, false, false, false)
        }
        assertEquals(expected, actual)
    }
    @Test
    fun testDoNavigateCommand() {
        val expected = "end of math"
        val actual = mathCat.run {
            it.setMathml(BASIC_MATHML)
            it.doNavigateCommand("MoveNext")
        }
        assertEquals(expected, actual)
    }
    @Test
    fun testGetNavigationMathml() {
        val expected = NavigationNode(" <math id='n1'>\n" +
                "  <mrow id='n2'>\n" +
                "    <mi id='n3'>y</mi>\n" +
                "    <mo id='n4'>=</mo>\n" +
                "    <mrow id='r0'>\n" +
                "      <mi id='n5'>x</mi>\n" +
                "      <mo id='n6'>+</mo>\n" +
                "      <mn id='n7'>2</mn>\n" +
                "    </mrow>\n" +
                "  </mrow>\n" +
                " </math>\n", 0)
        val actual = mathCat.run {
            it.setMathml(BASIC_MATHML)
            it.getNavigationMathml()
        }
        assertEquals(expected, actual)
    }
    @Test
    fun testGetNavigationMathmlId() {
        val expected = NavigationId("n1", 0)
        val actual = mathCat.run {
            it.setMathml(BASIC_MATHML)
            it.getNavigationMathmlId()
        }
        assertEquals(expected, actual)
    }
}
