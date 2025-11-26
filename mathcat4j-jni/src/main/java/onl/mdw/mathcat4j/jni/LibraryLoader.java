/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.jni;

/**
 * Interface for classes to load the JNI library.
 * Depending upon the implementation this may be as simple as loading, but it can also include steps such as extracting the library from an archive.
 */
public interface LibraryLoader {
    /**
     * Attempt to load the library.
     * @return If loading is successful then returns true, otherwise false.
     */
    boolean load();
}
