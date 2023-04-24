package com.dnguy38.lastplayed.data.search

import com.dnguy38.lastplayed.data.last_fm.Image

interface SearchResult {
    val name: String
    val image: List<Image>
    val type: SearchType
}

interface SearchResults {
    val list: List<SearchResult>
}
