package com.dnguy38.lastplayed.data.last_fm.responses

import com.dnguy38.lastplayed.data.last_fm.Image
import com.dnguy38.lastplayed.data.search.SearchResult
import com.dnguy38.lastplayed.data.search.SearchResults
import com.dnguy38.lastplayed.data.search.SearchType
import com.google.gson.annotations.SerializedName

data class TrackSearchResponse(
    val results: TrackSearchResults?
)

data class TrackSearchResults(
    @SerializedName("trackmatches") val matches: TrackMatches
): SearchResults {
    override val list: List<SearchResult>
        get() = matches.track
}

data class TrackMatches(
    val track: List<TrackMatch>
)

data class TrackMatch(
    override val name: String,
    val artist: String,
    val url: String,
    val streamable: String, // API current returning FIXME
    val listeners: Int,
    override val image: List<Image>,
    val mbid: String
): SearchResult {
    override val type: SearchType
        get() = SearchType.Track
}
