package com.example.ctplayer

import android.media.MediaPlayer
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object AudioManager {
    private val _currentAudioList: MutableLiveData<List<AudioModel>> = MutableLiveData()
    val currentAudioList: LiveData<List<AudioModel>> get() = _currentAudioList
    val currentAudioListSize: Int get() = _currentAudioList.value?.size ?: 0
    private val _currentAudio: MutableLiveData<AudioModel?> = MutableLiveData()
    val currentAudio: LiveData<AudioModel?> get() = _currentAudio
    val currentAudioIndex: Int get() = _currentAudioList.value?.indexOf(_currentAudio.value) ?: -1
    var isPlaying = false
    var mediaPlayer: MediaPlayer? = null

    fun setAudioList(audioList: List<AudioModel>) {
        _currentAudioList.postValue(audioList)
    }

    fun setCurrentAudio(audio: AudioModel) {
        _currentAudio.postValue(audio)
    }

    fun getCurrentAudioList(): List<AudioModel>? {
        return _currentAudioList.value
    }

    fun setCurrentAudio(index: Int) {
        _currentAudioList.value?.getOrNull(index)?.let {
            _currentAudio.postValue(it)
        }
    }

    fun getCurrentAudio(): AudioModel? {
        return _currentAudio.value
    }

    private fun modifyCurrentAudioIndex(delta: Int): AudioModel? {
        return currentAudioList.value?.let { list ->
            ((currentAudioIndex + delta) % list.size).let { newIndex ->
                list[newIndex].also { setCurrentAudio(it) }
            }
        }
    }

    fun getNextAudio(): AudioModel? = modifyCurrentAudioIndex(1)
    fun getPreviousAudio(): AudioModel? = modifyCurrentAudioIndex(-1)

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
//                   Toast.makeText(this@AudioManager, "Playback Error", Toast.LENGTH_SHORT).show()
                    true
                }
            } catch (e: Exception) {
//                Toast.makeText("Error setting up media player: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
        this@AudioManager.isPlaying = false
    }
}
