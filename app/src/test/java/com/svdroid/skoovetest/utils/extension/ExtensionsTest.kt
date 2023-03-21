package com.svdroid.skoovetest.utils.extension

import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {
    @Test
    fun testTimeStampToDurationWithPositiveValue() {
        val timestamp = 150000 // 2 minutes and 30 seconds
        val expected = "2:30"
        val result = timestamp.toLong().timeStampToDuration()
        assertEquals(expected, result)
    }

    @Test
    fun testTimeStampToDurationWithZeroValue() {
        val timestamp = 0 // 0 minutes and 0 seconds
        val expected = "0:00"
        val result = timestamp.toLong().timeStampToDuration()
        assertEquals(expected, result)
    }

    @Test
    fun testTimeStampToDurationWithNegativeValue() {
        val timestamp = -150000 // should return "--:--"
        val expected = "--:--"
        val result = timestamp.toLong().timeStampToDuration()
        assertEquals(expected, result)
    }

    @Test
    fun testTimeStampToDurationWithLargeValue() {
        val timestamp = 4000000 // 66 minutes and 40 seconds
        val expected = "66:40"
        val result = timestamp.toLong().timeStampToDuration()
        assertEquals(expected, result)
    }



    @Test
    fun testTimeStampToDurationWith2Hours() {
        val timestamp = 7210000 // 66 minutes and 40 seconds
        val expected = "120:10"
        val result = timestamp.toLong().timeStampToDuration()
        assertEquals(expected, result)
    }
}