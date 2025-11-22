/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.api;

/**
 * Node ID based navigation position.
 *
 * @param id the ID of the navigation node.
 * @param offset the character offset from the node.
 */
public record NavigationId(String id, int offset) {
}
