package com.svdroid.skoovetest.utils.extension

/**
 * Convert Int number to minutes and seconds
 */
fun Long.timeStampToDuration(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val remainingSeconds = (totalSeconds - (minutes * 60))

    return if (this < 0) "--:--"
    else "%d:%02d".format(minutes, remainingSeconds)
}