package com.example.ctplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AudioAdapter(private val audioList: ArrayList<AudioModel>) : RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.songs_list_item, parent, false)
        return AudioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val currentAudio = audioList[position]
        holder.bind(currentAudio)
    }

    override fun getItemCount(): Int = audioList.size

    class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.songsListCardSongName)
        private val artistTextView: TextView = itemView.findViewById(R.id.songsListCardArtist)
        private val durationTextView: TextView = itemView.findViewById(R.id.songsListCardDuration)

        fun bind(audio: AudioModel) {
            titleTextView.text = audio.displayName
            artistTextView.text = audio.artist
            durationTextView.text = audio.duration
        }
    }
}
