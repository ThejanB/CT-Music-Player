package com.example.ctplayer

import java.io.Serializable

data class AudioModel(
    var id: String,
    var displayName: String,
    var artist: String,
    var duration: String,
    var size: String,
    var data: String
) : Serializable
