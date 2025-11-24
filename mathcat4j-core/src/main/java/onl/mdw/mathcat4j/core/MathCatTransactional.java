/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
package onl.mdw.mathcat4j.core;

import onl.mdw.mathcat4j.api.MathCat;
import onl.mdw.mathcat4j.api.MathCatManager;

import java.util.function.Function;

public class MathCatTransactional implements MathCatManager {
    private final MathCat impl = MathCatImpl.INSTANCE;
    private static final MathCatTransactional INSTANCE = new MathCatTransactional();
    @Deprecated
    public static <T> T mathCAT(Function<? super MathCat, ? extends T> block) {
        return INSTANCE.run(block);
    }
    @Override
    public <T> T run(Function<? super MathCat, ? extends T> block) {
        synchronized (impl) {
            return block.apply(impl);
        }
    }
}