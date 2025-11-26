/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.jni;

import java.io.File;

/**
 * Load the library based on system properties.
 */
public class MathcatLibraryPathLoader implements LibraryLoader {
    @Override
    public boolean load() {
        final String mlp = System.getProperty("mathcat.library.path");
        if (mlp == null) {
            return false;
        }
        try {
            final String libName = System.getProperty("mathcat.library.name", "mathcat4j");
            System.load(new File(mlp, System.mapLibraryName(libName)).getAbsolutePath());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
