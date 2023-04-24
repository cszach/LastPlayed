package com.dnguy38.lastplayed.data.last_fm.responses

import com.dnguy38.lastplayed.data.last_fm.Image
import com.dnguy38.lastplayed.data.search.SearchResult
import com.dnguy38.lastplayed.data.search.SearchType
import com.google.gson.annotations.SerializedName

data class AlbumSearchResponse(
    val results: AlbumSearchResults
)

data class AlbumSearchResults(
    @SerializedName("albummatches") val matches: AlbumMatches
)

data class AlbumMatches(
    val album: List<AlbumMatch>
)

data class AlbumMatch(
    override val name: String,
    val artist: String,
    val url: String,
    override val image: List<Image>,
    val streamable: Int,
    val mbid: String
): SearchResult {
    override val type: SearchType
        get() = SearchType.Album
}
