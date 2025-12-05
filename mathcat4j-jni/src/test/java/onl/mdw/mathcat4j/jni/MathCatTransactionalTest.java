/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.jni;

import onl.mdw.mathcat4j.api.MathCat;
import onl.mdw.mathcat4j.api.NavigationId;
import onl.mdw.mathcat4j.api.NavigationNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MathCatTransactionalTest {
    private static final String BASIC_MATHML = "<math id=\"n1\"><mrow id=\"n2\"><mi id=\"n3\">y</mi><mo id=\"n4\">=</mo><mrow id=\"r0\"><mi id=\"n5\">x</mi><mo id=\"n6\">+</mo><mn id=\"n7\">2</mn></mrow></mrow></math>";
    private MathCatTransactional mathCat;
    @BeforeEach
    public void configureMathCatRules() {
        mathCat = new MathCatTransactional();
        mathCat.run(m -> {
            m.setRulesDir(System.getProperty("onl.mdw.mathcat4j.testRulesDir"));
            return null;
        });
    }
    @Test
    public void testGetVersion() {
        String expected = Arrays.stream(System.getProperty("onl.mdw.mathcat4j.testVersion").split("-")).findFirst().orElseThrow();
        assertThat(mathCat.run(MathCat::getVersion)).isEqualTo(expected);
    }
    @Test
    public void testSetInvalidMathml() {
        assertThatThrownBy(() -> mathCat.run(m -> {
            m.setMathml("Some random string");
            return null;
        })).isInstanceOf(RuntimeException.class);
    }
    @Test
    public void testSetMathmlReturnsMathml() {
        String expected = """
                 <math id='mkt-0'>
                  <mrow id='mkt-1'>
                    <mn id='mkt-2'>1</mn>
                    <mo id='mkt-3'>+</mo>
                    <mi id='mkt-4'>x</mi>
                  </mrow>
                 </math>
                """;
        String input = "<math id='mkt-0'><mrow id='mkt-1'><mn id='mkt-2'>1</mn><mo id='mkt-3'>+</mo><mi id='mkt-4'>x</mi></mrow></math>";
        assertThat(mathCat.<String>run(m -> m.setMathml(input))).isEqualTo(expected);
    }
    @Test
    public void testGetAndSetPreferences() {
        mathCat.run(m -> {
            m.setPreference("Volume", "100");
            assertThat(m.getPreference("Volume")).isEqualTo("100");
            m.setPreference("Volume", "50");
            assertThat(m.getPreference("Volume")).isEqualTo("50");
            return null;
        });
    }
    @Test
    public void testGetPreferenceInvalid() {
        assertThatThrownBy(() -> mathCat.run(m -> m.getPreference("SomeRandomInvalidPreference"))).isInstanceOf(RuntimeException.class);
    }
    @Test
    public void testGetBraille() {
        String expected = "⠽⠀⠨⠅⠀⠭⠬⠆";
        assertThat(mathCat.<String>run(m -> {
            m.setMathml(BASIC_MATHML);
            return m.getBraille();
        })).isEqualTo(expected);
    }
    @Test
    public void testGetBrailleForId() {
        String expected = "⠽⠀⣨⣅⠀⠭⠬⠆";
        assertThat(mathCat.<String>run(m -> {
            m.setMathml(BASIC_MATHML);
            return m.getBraille("n4");
        })).isEqualTo(expected);
    }
    @Test
    public void testGetNavigationBraille() {
        String expected = "⠽⠀⠨⠅⠀⠭⠬⠆";
        assertThat(mathCat.<String>run(m -> {
            m.setMathml(BASIC_MATHML);
            return m.getNavigationBraille();
        })).isEqualTo(expected);
    }
    @Test
    public void testGetSpokenText() {
        String expected = "y is equal to x plus 2";
        assertThat(mathCat.<String>run(m -> {
            m.setMathml(BASIC_MATHML);
            return m.getSpokenText();
        })).isEqualTo(expected);
    }
    @Test
    public void testGetOverviewText() {
        String expected = "y is equal to x plus 2";
        assertThat(mathCat.<String>run(m -> {
            m.setMathml(BASIC_MATHML);
            return m.getOverviewText();
        })).isEqualTo(expected);
    }
    @Test
    public void testDoNavigateKeypress() {
        String expected = "end of math";
        assertThat(mathCat.<String>run(m -> {
            m.setMathml(BASIC_MATHML);
            return m.doNavigateKeypress(39, false, false, false, false);
        })).isEqualTo(expected);
    }
    @Test
    public void testDoNavigateCommand() {
        String expected = "end of math";
        assertThat(mathCat.<String>run(m -> {
            m.setMathml(BASIC_MATHML);
            return m.doNavigateCommand("MoveNext");
        })).isEqualTo(expected);
    }
    @Test
    public void testGetNavigationMathml() {
        NavigationNode expected = new NavigationNode("""
                 <math id='n1'>
                  <mrow id='n2'>
                    <mi id='n3'>y</mi>
                    <mo id='n4'>=</mo>
                    <mrow id='r0'>
                      <mi id='n5'>x</mi>
                      <mo id='n6'>+</mo>
                      <mn id='n7'>2</mn>
                    </mrow>
                  </mrow>
                 </math>
                """, 0);
        assertThat(mathCat.<NavigationNode>run(m -> {
            m.setMathml(BASIC_MATHML);
            return m.getNavigationMathml();
        })).isEqualTo(expected);
    }
    @Test
    public void testGetNavigationMathmlId() {
        NavigationId expected = new NavigationId("n1", 0);
        assertThat(mathCat.<NavigationId>run(m -> {
            m.setMathml(BASIC_MATHML);
            return m.getNavigationMathmlId();
        })).isEqualTo(expected);
    }
    @Test
    public void testGetSupportedBrailleCodes() {
        String[] expected = new String[] {"ASCIIMath", "ASCIIMath-fi", "CMU", "LaTeX", "Nemeth", "Swedish", "UEB", "Vietnam"};
        assertThat(mathCat.run(MathCat::getSupportedBrailleCodes)).isEqualTo(expected);
    }
    @Test
    public void testGetSupportedLanguages() {
        String[] expected = new String[] {"de", "en", "en-gb", "es", "fi", "id", "nb", "sv", "vi", "zh-tw"};
        assertThat(mathCat.run(MathCat::getSupportedLanguages)).isEqualTo(expected);
    }
    @Test
    public void testGetSupportedSpeechStyles() {
        String[] expected = new String[] {"ClearSpeak", "SimpleSpeak"};
        assertThat(mathCat.<String[]>run(m -> m.getSupportedSpeechStyles("en"))).isEqualTo(expected);
    }
}
