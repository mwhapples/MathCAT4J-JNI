/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.jni;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import onl.mdw.mathcat4j.jni.libs.LibInfo;

import java.io.File;

/**
 * Extract native libraries using JNA.
 */
public class JnaLibraryLoader implements LibraryLoader {
    @Override
    public boolean load() {
        try {
            final File libFile = Native.extractFromResourcePath(String.format("%s/%s/%s", LibInfo.PATH_PREFIX, Platform.RESOURCE_PREFIX, System.mapLibraryName(LibInfo.LIB_NAME)));
            System.load(libFile.getAbsolutePath());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
