package com.example.ctplayer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctplayer.AudioAdapter
import com.example.ctplayer.AudioManager
import com.example.ctplayer.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAudioList()
    }

    private fun observeAudioList() {
        AudioManager.currentAudio.observe(viewLifecycleOwner) { audioList ->
            if (audioList == null || audioList.isEmpty()) {
                binding.noMusicTextView.visibility = View.VISIBLE
                binding.noMusicTextView.text = "Audio list not initialized or is empty"
            } else {
                binding.noMusicTextView.visibility = View.GONE
                binding.songsRecycler.layoutManager = LinearLayoutManager(context)
                binding.songsRecycler.adapter = AudioAdapter(audioList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
