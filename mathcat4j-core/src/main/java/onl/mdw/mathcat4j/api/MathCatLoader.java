/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.api;

import java.util.Optional;
import java.util.ServiceLoader;

/**
 * Class for loading MathCAT implementations.
 */
public class MathCatLoader {
    /**
     * The singleton instance.
     */
    public static final MathCatLoader INSTANCE = new MathCatLoader();
    private MathCatLoader() {}
    private final ServiceLoader<MathCatFactory> serviceLoader = ServiceLoader.load(MathCatFactory.class);

    /**
     * Gets the first located implementation of MathCAT.
     * @return An optional containing the first located implementation of MathCAT, otherwise empty.
     */
    public Optional<MathCatFactory> getMathCatFactory() {
        return serviceLoader.findFirst();
    }

    /**
     * Reloads the implementations of MathCAT.
     */
    public void reload() {
        serviceLoader.reload();
    }
}
