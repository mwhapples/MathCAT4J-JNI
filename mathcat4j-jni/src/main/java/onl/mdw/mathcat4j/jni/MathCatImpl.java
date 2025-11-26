/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.jni;

import onl.mdw.mathcat4j.api.MathCat;

import java.util.stream.Stream;

class MathCatImpl extends MathCatJni {
    private MathCatImpl() {
        Stream.Builder<LibraryLoader> loaderBuilder = Stream.builder();
        loaderBuilder.add(new MathcatLibraryPathLoader());
        loaderBuilder.add(new JnaLibraryLoader());
        loaderBuilder.build().filter(LibraryLoader::load).findFirst().orElseThrow(() -> new RuntimeException("Unable to load the native MathCAT library"));
        final String rulesDir = System.getProperty("onl.mdw.mathcat4j.rulesDir");
        if (rulesDir != null) {
            setRulesDir(rulesDir);
        }
    }
    final static MathCat INSTANCE = new MathCatImpl();
}
