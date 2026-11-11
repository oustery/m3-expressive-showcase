package com.example.m3expressive

import java.io.File
import org.junit.Assert.assertTrue
import org.junit.Test

class ArchitectureTest {
    @Test
    fun releaseBuildDefinesExactlyTheExpectedAbis() {
        val buildScript = File("build.gradle.kts").readText()
        listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64").forEach { abi ->
            assertTrue("Missing ABI split: $abi", buildScript.contains("\"$abi\""))
        }
        assertTrue("Universal APK must stay disabled", buildScript.contains("isUniversalApk = false"))
    }

    /**
     * [androidx.compose.runtime.saveable.rememberSaveable] only saves `MutableState`; the
     * specialized holders (`mutableIntStateOf` and friends) are rejected when the activity saves
     * its state, which crashes on rotation and process death.
     */
    @Test
    fun savedStateUsesSaveableMutableState() {
        val pattern = Regex("rememberSaveable\\s*\\{\\s*mutable(Int|Long|Float|Double)StateOf")
        val offenders = File("src/main/java")
            .walkTopDown()
            .filter { it.isFile && it.extension == "kt" }
            .filter { pattern.containsMatchIn(it.readText()) }
            .map { it.path }
            .toList()
        assertTrue(
            "Use rememberSaveable { mutableStateOf(...) } instead: $offenders",
            offenders.isEmpty(),
        )
    }
}
