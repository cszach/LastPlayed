package com.dnguy38.lastplayed.data.last_fm.responses

import com.dnguy38.lastplayed.data.last_fm.Image
import com.dnguy38.lastplayed.data.search.SearchResult
import com.dnguy38.lastplayed.data.search.SearchResults
import com.dnguy38.lastplayed.data.search.SearchType
import com.google.gson.annotations.SerializedName

data class ArtistSearchResponse(
    val results: ArtistSearchResults?
)

data class ArtistSearchResults(
    @SerializedName("artistmatches") val matches: ArtistMatches,
): SearchResults {
    override val list: List<SearchResult>
        get() = matches.artist
}

data class ArtistMatches(
    val artist: List<ArtistMatch>
)

data class ArtistMatch(
    override val name: String,
    val listeners: Int,
    val mbid: String,
    val url: String,
    val streamable: Int,
    override val image: List<Image>
): SearchResult {
    override val type: SearchType
        get() = SearchType.Artist
}
