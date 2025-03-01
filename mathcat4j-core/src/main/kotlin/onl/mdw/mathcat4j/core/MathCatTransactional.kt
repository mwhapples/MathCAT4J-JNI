/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
@file:JvmName("MathCatTransactional")
package onl.mdw.mathcat4j.core

import com.sun.jna.Native
import com.sun.jna.Platform
import onl.mdw.mathcat4j.api.MathCat
import onl.mdw.mathcat4j.api.MathCatJni
import java.io.File
import java.io.IOException

private object MathCatImpl : MathCatJni() {

    private fun extractLibrary(libraryResource: String): File? = try {
        Native.extractFromResourcePath("/META-INF/native/${System.mapLibraryName(libraryResource)}")
    } catch (e: IOException) {
        null
    }

    init {
        val baseLibName = "mathcat4j"
        val attemptLibraries = listOf("$baseLibName-${Platform.RESOURCE_PREFIX}", baseLibName)
        val libraryFile = System.getProperty("mathcat.library.path")?.let { File(it) }?.takeIf { it.exists() } ?: attemptLibraries.firstNotNullOfOrNull(::extractLibrary) ?: throw java.lang.RuntimeException("Unable to extract library, tried ${attemptLibraries.joinToString()}")
        System.load(libraryFile.absolutePath)
        System.getProperty("onl.mdw.mathcat4j.rulesDir")?.let { setRulesDir(it) }
    }
}

fun <T> mathCAT(block: MathCat.() -> T): T = synchronized(MathCatImpl) {
    MathCatImpl.block()
}