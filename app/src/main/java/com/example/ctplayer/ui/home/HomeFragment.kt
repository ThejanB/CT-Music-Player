package com.example.ctplayer.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctplayer.AudioAdapter
//import com.example.ctplayer.AudioAdapter
import com.example.ctplayer.AudioManager
import com.example.ctplayer.AudioModel
import com.example.ctplayer.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            // Adding a check to see if data has been loaded
            if (AudioManager.getAudioList() == null) {
                noMusicTextView.visibility = View.VISIBLE
                noMusicTextView.text = "Audio list not initialized"
            } else if (AudioManager.getAudioList()?.isEmpty() == true) {
                noMusicTextView.visibility = View.VISIBLE
                noMusicTextView.text = "Music NOT found"
            } else {
//                noMusicTextView.visibility = View.GONE
                noMusicTextView.text = "Music found ${AudioManager.getAudioList()?.size}"
                binding.songsRecycler.layoutManager = LinearLayoutManager(context)
                binding.songsRecycler.adapter = AudioAdapter(AudioManager.getAudioList()!!)

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
