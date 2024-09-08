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
        currentTimeTextView.text = "0:00"               // [ToDO] update and show current time
        timeLeftTextView.text = convertTime(audio.duration.toLong())    // [ToDO] update and show time left
        // imageView.setImageDrawable(...) // Set your image if applicable
        // Update your seekbar and timers here
    }

    private fun setupControls() {
        playButton.setOnClickListener {
            // Handle play/pause logic
        }

        previousButton.setOnClickListener {
            AudioManager.previousAudio()
        }

        nextButton.setOnClickListener {
            AudioManager.nextAudio()
        }

    }

    private fun convertTime(duration: Long): String {
        val minutes = duration / 1000 / 60
        val seconds = duration / 1000 % 60
        return "$minutes:$seconds"
    }
}
