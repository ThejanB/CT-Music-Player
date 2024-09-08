package com.example.ctplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object AudioManager {
    // Live data for the list of all audios
    private val _currentAudioList: MutableLiveData<List<AudioModel>> = MutableLiveData()
    val currentAudioList: LiveData<List<AudioModel>> get() = _currentAudioList

    // Set the audio list
    fun setAudioList(audioList: List<AudioModel>) {
        _currentAudioList.postValue(audioList)
    }

    // Get the audio list
    fun getCurrentAudioList(): List<AudioModel>? {
        return _currentAudioList.value
    }

    // Live data for the currently playing audio
    private val _currentAudio: MutableLiveData<AudioModel?> = MutableLiveData()
    val currentAudio: LiveData<AudioModel?> get() = _currentAudio

    // Set the current audio by reference
    fun setCurrentAudio(audio: AudioModel) {
        _currentAudio.postValue(audio)
    }

    // Set the current audio by index
    fun setCurrentAudio(index: Int) {
        _currentAudioList.value?.let {
            if (index in it.indices) {
                _currentAudio.postValue(it[index])
            }
        }
    }

    // Get the currently playing audio
    fun getCurrentAudio(): AudioModel? {
        return _currentAudio.value
    }
}
