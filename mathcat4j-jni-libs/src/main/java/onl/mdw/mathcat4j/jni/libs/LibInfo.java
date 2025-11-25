/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.jni.libs;

/**
 * A class containing information about the native libs in this bundle.
 */
public class LibInfo {
    private LibInfo() {}

    /**
     * The prefix path to the native libraries in this bundle.
     * This prefix is the path without the platform specific element, native libraries will be in platform specific subdirectories.
     */
    public static final String PATH_PREFIX = "/onl/mdw/mathcat4j/jni/libs";
    /**
     * The library name.
     * This is the base library name without any platform specific parts such as lib prefixes or file extensions.
     */
    public static final String LIB_NAME = "mathcat4j";
}
