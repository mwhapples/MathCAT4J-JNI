/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.api;

import java.util.function.Function;

/**
 * Manages access to MathCAT.
 * Precisely what this does is implementation-dependent but may involve ensuring synchronous access.
 */
public interface MathCatManager {
    /**
     * Run a block of code with MathCAT.
     * @param block The block of code to be run.
     * @return The return value from the block.
     * @param <T> The type returned by the block.
     */
    <T> T run(Function<? super MathCat, ? extends T> block);
}
