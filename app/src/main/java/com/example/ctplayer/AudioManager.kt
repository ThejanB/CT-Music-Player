package com.example.ctplayer

import android.media.MediaPlayer
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object AudioManager {
    ////////// list of all audios //////////
    private val _currentAudioList: MutableLiveData<List<AudioModel>> = MutableLiveData()
    val currentAudioList: LiveData<List<AudioModel>> get() = _currentAudioList
    val currentAudioListSize: Int get() = _currentAudioList.value?.size ?: 0

    // Set the audio list
    fun setAudioList(audioList: List<AudioModel>) {
        _currentAudioList.postValue(audioList)
    }

    // Get the audio list
    fun getCurrentAudioList(): List<AudioModel>? {
        return _currentAudioList.value
    }

    ////////// Currently playing audio //////////
    private val _currentAudio: MutableLiveData<AudioModel?> = MutableLiveData()
    val currentAudio: LiveData<AudioModel?> get() = _currentAudio
    val currentAudioIndex: Int get() = _currentAudioList.value?.indexOf(_currentAudio.value) ?: -1

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

    // Get the previous audio
    fun getPreviousAudio(): AudioModel? {
        currentAudioList.value?.let { list ->
            val previousIndex = if (currentAudioIndex > 0) currentAudioIndex - 1 else list.size - 1
            val previousAudio = list[previousIndex]
            setCurrentAudio(previousAudio)
            return previousAudio
        }
        return null
    }

    // Get the next audio
    fun getNextAudio(): AudioModel? {
        currentAudioList.value?.let { list ->
            val nextIndex = if (currentAudioIndex < list.size - 1) currentAudioIndex + 1 else 0
            val nextAudio = list[nextIndex]
            setCurrentAudio(nextAudio)
            return nextAudio
        }
        return null
    }

    ////////// Media Player //////////
    var isPlaying = false
    var mediaPlayer: MediaPlayer? = null

    // Set up the media player
    fun setupMediaPlayer(audio: AudioModel) {
        mediaPlayer?.release()  // Release any previously playing media player
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(audio.data)  // Set the data source to the new audio
                prepareAsync()  // Prepare the media player asynchronously
                setOnPreparedListener {
                    start() // Start the media player when it's prepared
                    this@AudioManager.isPlaying = true
                }
                setOnCompletionListener {
                    reset()
                    this@AudioManager.isPlaying = false
                }
                setOnErrorListener { _, _, _ ->
//                    Toast.makeText(this@AudioManager, "Playback Error", Toast.LENGTH_SHORT).show()
                    true
                }
            } catch (e: Exception) {
//                Toast.makeText(this@AudioManager, "Error setting up media player: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
