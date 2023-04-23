package com.dnguy38.lastplayed.data.last_fm

data class Artist(
    val name: String,
    val mbid: String,
    val url: String,
    val image: List<Image>,
    val streamable: Int,
    val ontour: Int,
    val stats: ArtistStats,
    val similar: List<SimilarArtist>,
    val tags: List<Tag>,
    val bio: Bio
)