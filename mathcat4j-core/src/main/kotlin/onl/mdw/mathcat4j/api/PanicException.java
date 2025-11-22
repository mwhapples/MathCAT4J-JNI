/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.api;

/**
 * Exception to indicate when there is a panic in the rust code.
 *
 * If the JNI bindings detect a panic in the MathCAT rust code then an exception of this type will be thrown. In many cases it is said that you should not use panic for control flow, so the main reason for this exception type is to allow JVM code catch the panic and close down the JVM in a controlled way.
 */
public class PanicException extends RuntimeException {
    public PanicException(String message) {
        super(message);
    }
}
