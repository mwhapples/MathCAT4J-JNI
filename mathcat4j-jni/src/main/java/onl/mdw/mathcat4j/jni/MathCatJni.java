/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
package onl.mdw.mathcat4j.jni;

import onl.mdw.mathcat4j.api.MathCat;
import onl.mdw.mathcat4j.api.NavigationId;
import onl.mdw.mathcat4j.api.NavigationNode;
import onl.mdw.mathcat4j.api.PositionRange;

/**
 * Java side API for the JNI bindings to MathCAT.
 *
 * This exposes the MathCAT functions to JVM based applications. This does not deal with any thread safety or other concerns which will be dealt with by subclasses of this class.
 */
public abstract class MathCatJni implements MathCat {
    @Override
    native public String getVersion();
    @Override
    native public void setRulesDir(String dir);
    @Override
    native public String getPreference(String name);
    @Override
    native public void setPreference(String name, String value);
    @Override
    native public String setMathml(String mathmlStr);
    @Override
    native public String getNavigationBraille();
    @Override
    native public String getBraille(String navigationId);
    @Override
    native public String getSpokenText();
    @Override
    native public String getOverviewText();
    @Override
    native public String doNavigateKeypress(
        int key,
        boolean shiftKey,
        boolean controlKey,
        boolean altKey,
        boolean metaKey
    );

    @Override
    native public String doNavigateCommand(String command);
    @Override
    native public NavigationNode getNavigationMathml();
    @Override
    native public NavigationId getNavigationMathmlId();
    @Override
    native public void setNavigationNode(String id, int offset);
    @Override
    native public PositionRange getBraillePosition();
    @Override
    native public NavigationNode getNavigationNodeFromBraillePosition(int position);
}
