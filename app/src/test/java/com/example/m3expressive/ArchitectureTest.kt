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
}
