package com.dnguy38.lastplayed.data.last_fm

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
