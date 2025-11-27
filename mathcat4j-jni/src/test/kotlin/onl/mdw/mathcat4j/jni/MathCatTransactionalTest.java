/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.jni;

import onl.mdw.mathcat4j.api.MathCat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

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
}
