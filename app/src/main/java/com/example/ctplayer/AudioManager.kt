package com.example.ctplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object AudioManager {
    private val _currentAudio: MutableLiveData<List<AudioModel>> = MutableLiveData()
    val currentAudio: LiveData<List<AudioModel>> get() = _currentAudio

    fun setAudioList(audio: List<AudioModel>) {
        _currentAudio.postValue(audio)
    }

    fun getCurrentAudioList(): List<AudioModel>? {
        return _currentAudio.value
    }
}
