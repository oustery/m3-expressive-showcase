package com.example.m3expressive

import org.junit.Assert.assertEquals
import org.junit.Test

class ArchitectureTest {
    @Test
    fun allFourReleaseAbisAreDocumented() {
        val supportedAbis = listOf("arm64-v8a", "armeabi-v7a", "x86_64", "x86")
        assertEquals(4, supportedAbis.distinct().size)
    }
}
