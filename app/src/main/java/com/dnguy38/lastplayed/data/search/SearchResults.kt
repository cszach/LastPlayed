package com.dnguy38.lastplayed.data.search

import com.dnguy38.lastplayed.data.last_fm.Image
import com.dnguy38.lastplayed.data.last_fm.responses.AlbumMatches

interface SearchResult {
    val name: String
    val image: List<Image>
    val type: SearchType
}

interface SearchResults {
    val list: List<SearchResult>
}

class AlbumSearchResults(private val matches: AlbumMatches) : SearchResults {
    override val list: List<SearchResult>
        get() = matches.album
}
