package com.example.ctplayer

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ctplayer.databinding.ActivityMainBinding
import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.widget.Toast
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var songsList: ArrayList<AudioModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAudioData {
            setupNavigation()
        }
    }

    private fun setupAudioData(onComplete: () -> Unit) {
        if (!checkPermission()) {
            requestPermission()
        }
        val cursor: Cursor? = readMusicFiles()
        cursor?.use {
            while (it.moveToNext()) {
                // Required columns
                val idIndex = it.getColumnIndex(MediaStore.Audio.Media._ID)
                val displayNameIndex = it.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
                val dataIndex = it.getColumnIndex(MediaStore.Audio.Media.DATA)

                // Optional columns
                val artistIndex = it.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                val durationIndex = it.getColumnIndex(MediaStore.Audio.Media.DURATION)
                val sizeIndex = it.getColumnIndex(MediaStore.Audio.Media.SIZE)

                // Check required columns are valid
                if (idIndex != -1 && displayNameIndex != -1 && dataIndex != -1) {
                    val id = it.getString(idIndex)
                    val displayName = it.getString(displayNameIndex)
                    val data = it.getString(dataIndex)

                    // Optional values, provide defaults or nulls if indexes are -1
                    val artist = if (artistIndex != -1) it.getString(artistIndex) else "Unknown"
                    val duration = if (durationIndex != -1) it.getString(durationIndex) else "Unknown"
                    val size = if (sizeIndex != -1) it.getString(sizeIndex) else "Unknown"

                    val audioModel = AudioModel(id, displayName, artist, duration, size, data)

                    if (File(audioModel.data).exists()) {
                        songsList.add(audioModel)
                    }
                } else {
                    Log.e("DataRetrieval", "Required columns not found in the cursor.")
                }
            }
        }
        AudioManager.setAudioList(songsList)
        onComplete()
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profile
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun checkPermission() : Boolean{
        val requiredPermission = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        return ContextCompat.checkSelfPermission(this, requiredPermission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        val requiredPermission = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, requiredPermission)) {
            Toast.makeText(this, getString(R.string.permission_required_toast), Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(requiredPermission), 1) // 1 is request code (a random number)
        }

    }

    private fun readMusicFiles(): Cursor? {
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DATA
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

        // Querying the media store to fetch music files
        val cursor: Cursor? = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null
        )
        return cursor
    }

}