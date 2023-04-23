package com.dnguy38.lastplayed.data.last_fm.responses

import com.dnguy38.lastplayed.data.last_fm.Image
import com.dnguy38.lastplayed.data.last_fm.Streamable
import com.dnguy38.lastplayed.data.last_fm.TrackArtist

data class TopTracksResponse(
    val tracks: TopTracks
)

data class TopTracks(
    val track: List<TopTrack>,
)

data class TopTrack(
    val name: String,
    val duration: Int,
    val listeners: Int,
    val mbid: String,
    val url: String,
    val streamable: Streamable,
    val artist: TrackArtist,
    val image: List<Image>
)