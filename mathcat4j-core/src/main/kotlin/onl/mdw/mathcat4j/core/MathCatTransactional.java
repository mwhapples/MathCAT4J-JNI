/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
package onl.mdw.mathcat4j.core;

import onl.mdw.mathcat4j.api.MathCat;

import java.util.function.Function;

public class MathCatTransactional {
    private MathCatTransactional() {}
    public static <T> T mathCAT(Function<MathCat, T> block) {
        synchronized (MathCatImpl.INSTANCE) {
            return block.apply(MathCatImpl.INSTANCE);
        }
    }
}