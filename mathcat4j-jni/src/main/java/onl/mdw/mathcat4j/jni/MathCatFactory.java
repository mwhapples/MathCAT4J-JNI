/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.jni;

import org.jspecify.annotations.NonNull;

/**
 * Factory for creating MathCAT instances using the JNI bindings.
 */
public class MathCatFactory implements onl.mdw.mathcat4j.api.MathCatFactory {
    private static final @NonNull MathCatTransactional MATHCAT_INSTANCE = new MathCatTransactional();

    /**
     * Create a MathCatTransactional instance using the JNI bindings.
     * @return The MathCatTransactional instance.
     */
    @Override
    public @NonNull MathCatTransactional create() {
        return MATHCAT_INSTANCE;
    }
}
