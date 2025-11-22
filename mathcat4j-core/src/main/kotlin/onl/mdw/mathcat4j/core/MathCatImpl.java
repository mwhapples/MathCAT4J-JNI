/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.core;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import onl.mdw.mathcat4j.api.MathCat;
import onl.mdw.mathcat4j.api.MathCatJni;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

class MathCatImpl extends MathCatJni {
    private MathCatImpl() {
        final List<String> baseLibName = List.of("mathcat4j");
        final File libraryFile = baseLibName.stream().flatMap(libName -> {
            String mlp = System.getProperty("mathcat.library.path");
            File libFile = null;
            if (mlp != null) {
                File f = new File(mlp, System.mapLibraryName(libName));
                if (f.exists()) {
                    libFile = f;
                }
            }
            if (libFile == null) {
                libFile = extractLibrary(libName);
            }
            return Stream.ofNullable(libFile);
        }).findFirst().orElseThrow(() -> new RuntimeException(String.format("Unable to extract library, tried %s", String.join(", ", baseLibName))));
        System.load(libraryFile.getAbsolutePath());
        final String rulesDir = System.getProperty("onl.mdw.mathcat4j.rulesDir");
        if (rulesDir != null) {
            setRulesDir(rulesDir);
        }
    }
    private File extractLibrary(String libraryResource) {
        try {
            return Native.extractFromResourcePath(String.format("/onl/mdw/mathcat4j/%s/%s", Platform.RESOURCE_PREFIX, System.mapLibraryName(libraryResource)));
        } catch (IOException e) {
            return null;
        }
    }
    static MathCat INSTANCE = new MathCatImpl();
}
