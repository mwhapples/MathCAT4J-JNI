/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
package onl.mdw.mathcat4j.core

import onl.mdw.mathcat4j.api.NavigationId
import onl.mdw.mathcat4j.api.NavigationNode
import onl.mdw.mathcat4j.core.MathCatTransactional.mathCAT
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

// Keep the setRulesDir tests separate, we don't need to have setRulesDir called before, and we probably want them separate in the reports as well.
class MathCatRulesDirTests {
    @Test
    fun testExceptionForInvalidRulesDirectory() {
        val invalidRulesDir = File(System.getProperty("onl.mdw.mathcat4j.testRulesDir"), "invalidDir").absolutePath
        val exceptionMessage = assertFailsWith(RuntimeException::class) { mathCAT { it.setRulesDir(invalidRulesDir) } }
        val expected = "set_rules_dir: could not canonicalize path $invalidRulesDir:"
        assertContains(exceptionMessage.message ?: "", expected)
    }
    @Test
    fun testSetRulesDirectoryCorrectlyDoesNotExcept() {
        mathCAT { it.setRulesDir(System.getProperty("onl.mdw.mathcat4j.testRulesDir")) }
    }
}

const val BASIC_MATHML = "<math id=\"n1\"><mrow id=\"n2\"><mi id=\"n3\">y</mi><mo id=\"n4\">=</mo><mrow id=\"r0\"><mi id=\"n5\">x</mi><mo id=\"n6\">+</mo><mn id=\"n7\">2</mn></mrow></mrow></math>"

class MathCatTest {
    @BeforeTest
    fun configureRules() {
        mathCAT { it.setRulesDir(System.getProperty("onl.mdw.mathcat4j.testRulesDir")) }
    }
    @Test
    fun testGetVersion() {
        assertEquals(System.getProperty("onl.mdw.mathcat4j.testVersion").takeWhile { it != '-' }, mathCAT { it.getVersion() })
    }
    @Test
    fun testSetInvalidMathml() {
        val mathml = "Some random string"
        assertFailsWith(RuntimeException::class) { mathCAT { it.setMathml(mathml) } }
    }
    @Test
    fun testReturnsWhenSetValidMathml() {
        val someMathml = "<math id='mkt-0'><mrow id='mkt-1'><mn id='mkt-2'>1</mn><mo id='mkt-3'>+</mo><mi id='mkt-4'>x</mi></mrow></math>"
        val expectedMathml = " <math id='mkt-0'>\n" +
                "  <mrow id='mkt-1'>\n" +
                "    <mn id='mkt-2'>1</mn>\n" +
                "    <mo id='mkt-3'>+</mo>\n" +
                "    <mi id='mkt-4'>x</mi>\n" +
                "  </mrow>\n" +
                " </math>\n"
        assertEquals(expectedMathml, mathCAT { it.setMathml(someMathml) })
    }
    @Test
    fun testSetAndGetPreference(): Unit = mathCAT {
        assertEquals("100.0", it.getPreference("Volume"))
        it.setPreference("Volume", "50")
        assertEquals("50", it.getPreference("Volume"))
    }
    @Test
    fun testGetpreferenceInvalid() {
        assertFailsWith(RuntimeException::class) { mathCAT { it.getPreference("SomeRandomInvalidPreference") } }
    }
    @Test
    fun testGetBrailleAll() {
        val expected = "⠽⠀⠨⠅⠀⠭⠬⠆"
        mathCAT {
            it.setMathml(BASIC_MATHML)
            assertEquals(expected, it.getBraille())
        }
    }
    @Test
    fun testGetBrailleForId() {
        val expected = "⠽⠀⣨⣅⠀⠭⠬⠆"
        mathCAT {
            it.setMathml(BASIC_MATHML)
            assertEquals(expected, it.getBraille("n4"))
        }
    }
    @Test
    fun testGetSpokenText() {
        val expected = "y is equal to x plus 2"
        val actual = mathCAT {
            it.setMathml(BASIC_MATHML)
            it.getSpokenText()
        }
        assertEquals(expected, actual)
    }
    @Test
    fun testGetOverviewText() {
        val expected = "y is equal to x plus 2"
        val actual = mathCAT {
            it.setMathml(BASIC_MATHML)
            it.getOverviewText()
        }
        assertEquals(expected, actual)
    }
    @Test
    fun testDoNavigateKeypress() {
        val expected = "end of math"
        val actual = mathCAT {
            it.setMathml(BASIC_MATHML)
            it.doNavigateKeypress(39, false, false, false, false)
        }
        assertEquals(expected, actual)
    }
    @Test
    fun testDoNavigateCommand() {
        val expected = "end of math"
        val actual = mathCAT {
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
        val actual = mathCAT {
            it.setMathml(BASIC_MATHML)
            it.getNavigationMathml()
        }
        assertEquals(expected, actual)
    }
    @Test
    fun testGetNavigationMathmlId() {
        val expected = NavigationId("n1", 0)
        val actual = mathCAT {
            it.setMathml(BASIC_MATHML)
            it.getNavigationMathmlId()
        }
        assertEquals(expected, actual)
    }
}
