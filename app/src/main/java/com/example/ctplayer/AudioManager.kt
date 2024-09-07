package com.example.ctplayer

import android.util.Log

object AudioManager {
    private var currentAudio: ArrayList<AudioModel>? = null
    private var currentAudioIndex = -1

    fun setAudioList(audio: ArrayList<AudioModel>) {
        currentAudio = audio
        Log.d("AudioManager.kt DataRetrieval", "Audio list set. ${audio.size}")
        Log.d("AudioManager.kt DataRetrieval", "Audio list set 2. ${currentAudio!!.size}")
    }

    fun getAudioList(): ArrayList<AudioModel>? {
        Log.d("AudioManager.kt DataRetrieval", "Audio list retrieved. ${currentAudio?.size}")
        return currentAudio
    }

    fun setCurrentAudioIndex(index: Int) {
        currentAudioIndex = index
    }

    fun getCurrentAudio(): AudioModel? {
        return if (currentAudioIndex != -1) {
            currentAudio?.get(currentAudioIndex)
        } else {
            null
        }
    }

}