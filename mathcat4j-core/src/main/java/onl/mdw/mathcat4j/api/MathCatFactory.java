/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.api;

/**
 * Factory for creating MathCatManager instances.
 */
public interface MathCatFactory {
    /**
     * Create an instance of MathCatManager.
     * @return A MathCatManager instance.
     */
    MathCatManager create();
}
