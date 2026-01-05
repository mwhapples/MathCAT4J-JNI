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
import org.jspecify.annotations.NonNull;

/**
 * Java side API for the JNI bindings to MathCAT.
 * This exposes the MathCAT functions to JVM based applications. This does not deal with any thread safety or other concerns which will be dealt with by subclasses of this class.
 */
public abstract class MathCatJni implements MathCat {
    @Override
    native public @NonNull String getVersion();
    @Override
    native public void setRulesDir(@NonNull String dir);
    @Override
    native public @NonNull String getPreference(@NonNull String name);
    @Override
    native public void setPreference(@NonNull String name, @NonNull String value);
    @Override
    native public @NonNull String setMathml(@NonNull String mathmlStr);
    @Override
    native public @NonNull String getNavigationBraille();
    @Override
    native public @NonNull String getBraille(@NonNull String navigationId);
    @Override
    native public @NonNull String getSpokenText();
    @Override
    native public @NonNull String getOverviewText();
    @Override
    native public @NonNull String doNavigateKeypress(
        int key,
        boolean shiftKey,
        boolean controlKey,
        boolean altKey,
        boolean metaKey
    );

    @Override
    native public @NonNull String doNavigateCommand(@NonNull String command);
    @Override
    native public @NonNull NavigationNode getNavigationMathml();
    @Override
    native public @NonNull NavigationId getNavigationMathmlId();
    @Override
    native public void setNavigationNode(@NonNull String id, int offset);
    @Override
    native public @NonNull PositionRange getBraillePosition();
    @Override
    native public @NonNull NavigationNode getNavigationNodeFromBraillePosition(int position);
    @Override
    native public String[] getSupportedBrailleCodes();
    @Override
    native public String[] getSupportedLanguages();
    @Override
    native public String[] getSupportedSpeechStyles(@NonNull String lang);
}
