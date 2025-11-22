/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
@file:JvmName("MathCatTransactional")
package onl.mdw.mathcat4j.core

import onl.mdw.mathcat4j.api.MathCat

fun <T> mathCAT(block: MathCat.() -> T): T = synchronized(MathCatImpl.INSTANCE) {
    MathCatImpl.INSTANCE.block()
}