/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.jni.test;

import io.questdb.jar.jni.JarJniLoader;
import onl.mdw.mathcat4j.jni.LibraryLoader;
import onl.mdw.mathcat4j.jni.libs.LibInfo;

public class TestLibraryLoader implements LibraryLoader {
    @Override
    public boolean load() {
        try {
            JarJniLoader.loadLib(LibInfo.class, LibInfo.PATH_PREFIX, LibInfo.LIB_NAME);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
