/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2025 Michael Whapples
 */
package onl.mdw.mathcat4j.api;

/**
 * Node based navigation position.
 *
 * @param node The node XML.
 * @param offset The character offset into the node.
 */
public record NavigationNode(String node, int offset) {
}
