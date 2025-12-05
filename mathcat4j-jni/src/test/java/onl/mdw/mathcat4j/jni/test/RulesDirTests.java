/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.jni.test;

import onl.mdw.mathcat4j.jni.MathCatFactory;
import onl.mdw.mathcat4j.jni.MathCatTransactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RulesDirTests {
    private MathCatTransactional mathCat;
    @BeforeEach
    public void configureMathCat() {
        mathCat = new MathCatFactory().create();
    }
    @Test
    public void testSetRulesDirInvalid() {
        String invalidRulesDir = new File(System.getProperty("onl.mdw.mathcat4j.testRulesDir"), "invalidDir").getAbsolutePath();
        assertThatThrownBy(() -> mathCat.run(m -> {
            m.setRulesDir(invalidRulesDir);
            return null;
        })).isInstanceOf(RuntimeException.class).hasMessageContaining(String.format("set_rules_dir: could not canonicalize path %s:", invalidRulesDir));
    }
    @Test
    public void testSetRulesDirValid() {
        assertDoesNotThrow(() -> mathCat.run(m -> {
            m.setRulesDir(System.getProperty("onl.mdw.mathcat4j.testRulesDir"));
            return null;
        }));
    }
}
