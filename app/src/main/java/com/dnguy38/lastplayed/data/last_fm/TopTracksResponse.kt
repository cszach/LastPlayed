package com.dnguy38.lastplayed.data.last_fm

data class TopTracks(
    val track: List<TopTrack>,
)

data class TopTracksResponse(
    val tracks: TopTracks
)