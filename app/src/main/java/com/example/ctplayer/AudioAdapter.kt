package com.example.ctplayer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ctplayer.ui.audioCard.AudioCard

class AudioAdapter(private val audioList: List<AudioModel>) : RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.songs_list_item, parent, false)
        return AudioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val audio = audioList[position]
        holder.bind(audio)

        holder.itemView.setOnClickListener {
            // Set the current audio in AudioManager
            AudioManager.setCurrentAudio(audio)

            // navigate to the AudioCard
            val context = holder.itemView.context
            val intent = Intent(context, AudioCard::class.java).apply {
                putExtra("AUDIO_ID", audio.id)
            }
            context.startActivity(intent)


        }
    }

    override fun getItemCount(): Int = audioList.size

    class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val songImage: ImageView = itemView.findViewById(R.id.songsListCardImage)
        private val songName: TextView = itemView.findViewById(R.id.songsListCardSongName)
        private val artistName: TextView = itemView.findViewById(R.id.songsListCardArtist)
        private val songDuration: TextView = itemView.findViewById(R.id.songsListCardDuration)

        fun bind(audio: AudioModel) {
            songName.text = audio.displayName
            artistName.text = audio.artist
            // [TODO - currently duration is shown in milliseconds, convert it to minutes and seconds]
            songDuration.text = audio.duration
            // [TODO] - Set an image if have image data or use a placeholder / Default image
            songImage.setImageResource(R.drawable.baseline_person_24)  // [TODO] - Replace with actual image handling logic
        }
    }
}
