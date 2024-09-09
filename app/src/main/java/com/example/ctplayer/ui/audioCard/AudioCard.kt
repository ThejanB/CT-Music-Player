package com.example.ctplayer.ui.audioCard

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.ctplayer.AudioManager
import com.example.ctplayer.AudioModel
import com.example.ctplayer.CommonHelperService
import com.example.ctplayer.R

class AudioCard : AppCompatActivity() {

    private lateinit var songNameTextView: TextView
    private lateinit var artistNameTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var seekBar: SeekBar
    private lateinit var currentTimeTextView: TextView
    private lateinit var timeLeftTextView: TextView
    private lateinit var previousButton: ImageButton
    private lateinit var playButton: ImageButton
    private lateinit var nextButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_card)

        initViews()
        observeCurrentAudio()
        setupControls()
        AudioManager.setupMediaPlayer(AudioManager.currentAudio.value!!)
    }

    private fun initViews() {
        songNameTextView = findViewById(R.id.audioCardSongName)
        artistNameTextView = findViewById(R.id.audioCardArtistName)
        imageView = findViewById(R.id.audioCardImage)
        seekBar = findViewById(R.id.audioCardSeekBar)
        currentTimeTextView = findViewById(R.id.audioCardCurrentTime)
        timeLeftTextView = findViewById(R.id.audioCardTimeLeft)
        previousButton = findViewById(R.id.audioCardPreviousButton)
        playButton = findViewById(R.id.audioCardPlayButton)
        nextButton = findViewById(R.id.audioCardNextButton)
    }

    private fun observeCurrentAudio() {
        AudioManager.currentAudio.observe(this, Observer { audio ->
            audio?.let {
                updateUI(it)
            }
        })
    }

    private fun updateUI(audio: AudioModel) {
        songNameTextView.text = audio.displayName
        artistNameTextView.text = audio.artist
        currentTimeTextView.text = getString(R.string.initial_time_0_00)               // [ToDO] update and show current time
        timeLeftTextView.text = CommonHelperService.convertTimeHHMMSS(audio.duration.toLong())    // [ToDO] update and show time left
        // Update your seekbar and timers here
    }

    private fun setupControls() {
        previousButton.setOnClickListener {
            AudioManager.getPreviousAudio()?.let {
                updateUI(it)
                AudioManager.setupMediaPlayer(it)
                playButton.setImageResource(R.drawable.baseline_pause_circle_outline_24)
            }
        }

        nextButton.setOnClickListener {
            AudioManager.getNextAudio()?.let {
                updateUI(it)
                AudioManager.setupMediaPlayer(it)
                playButton.setImageResource(R.drawable.baseline_pause_circle_outline_24)
            }
        }

        playButton.setOnClickListener {
            AudioManager.mediaPlayer?.let {
                if (AudioManager.isPlaying) {
                    it.pause()
                    playButton.setImageResource(R.drawable.baseline_play_circle_outline_24)
                } else {
                    it.start()
                    playButton.setImageResource(R.drawable.baseline_pause_circle_outline_24)
                }
                AudioManager.isPlaying = !AudioManager.isPlaying
            }
        }
    }

}
