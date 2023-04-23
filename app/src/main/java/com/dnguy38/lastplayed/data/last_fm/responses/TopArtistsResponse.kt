package com.dnguy38.lastplayed.data.last_fm.responses

import com.dnguy38.lastplayed.data.last_fm.Image
import com.google.gson.annotations.SerializedName

data class TopArtistsResponse(
    @SerializedName("topartists") val topArtists: TopArtists
)

data class TopArtists(
    val artist: List<TopArtist>
)

data class TopArtist(
    val name: String,
    val listeners: Int,
    val mbid: String,
    val url: String,
    val streamable: Int,
    val image: List<Image>
)



