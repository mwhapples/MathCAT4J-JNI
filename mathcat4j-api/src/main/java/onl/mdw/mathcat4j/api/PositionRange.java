/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.api;

/**
 * Wrapper for position ranges where there is a start and end position.
 * @param start The start position.
 * @param end The end position.
 */
public record PositionRange(int start, int end) {
}
