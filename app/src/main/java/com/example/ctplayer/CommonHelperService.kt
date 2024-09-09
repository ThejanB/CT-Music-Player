package com.example.ctplayer

import java.util.Locale

object CommonHelperService {
    fun convertTimeHHMMSS(duration: Long): String {
        val hours = duration / 3600000
        val minutes = (duration / 60000) % 60
        val seconds = (duration / 1000) % 60
        return if (hours > 0) {
            String.format(Locale.ROOT,"%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.ROOT, "%d:%02d", minutes, seconds)
        }
    }
}
