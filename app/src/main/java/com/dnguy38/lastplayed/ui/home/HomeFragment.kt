package com.dnguy38.lastplayed.ui.home

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dnguy38.lastplayed.R
import com.dnguy38.lastplayed.data.last_fm.TopTrack
import com.dnguy38.lastplayed.databinding.FragmentHomeBinding
import java.io.InputStream
import java.net.URL

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val topTracks = binding.topTracks

        topTracks.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.topTracks.observe(viewLifecycleOwner) {
            topTracks.adapter = TrackAdapter(it)
        }

        viewModel.getTopTracks()

        return binding.root
    }

    private inner class TrackViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private lateinit var track: TopTrack
        private val card: CardView = view.findViewById(R.id.track_cardView)
        private val imageView: ImageView = view.findViewById(R.id.track_imageView)
        private val titleView: TextView = view.findViewById(R.id.track_title_textView)
        private val artistView: TextView = view.findViewById(R.id.track_artist_textView)

        init {
            // TODO: Initialize listeners
        }

        fun bind(track: TopTrack, isLast: Boolean) {
            this.track = track

            val image = track.image.filter {
                it.size == "large"
            }[0]

            // TODO: do this in a non-blocking way

            lateinit var imageDrawable: Drawable

            val networkThread = Thread {
                val imageStream = URL(image.url).content as InputStream
                imageDrawable = Drawable.createFromStream(imageStream, null)!!
                imageView.setImageDrawable(imageDrawable)
            }

            networkThread.start()
            networkThread.join()

            titleView.text = track.name
            artistView.text = track.artist.name

            if (isLast) {
                val marginParams = card.layoutParams as MarginLayoutParams
                marginParams.marginEnd = marginParams.marginStart
            }
        }
    }

    private inner class TrackAdapter(private val list: List<TopTrack>): RecyclerView.Adapter<TrackViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
            val view = layoutInflater.inflate(R.layout.track_item, parent, false)

            return TrackViewHolder(view)
        }

        override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
            holder.bind(list[position], position == itemCount - 1)
        }

        override fun getItemCount() = list.size
    }
}