package com.dnguy38.lastplayed.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnguy38.lastplayed.data.last_fm.responses.AlbumSearchResponse
import com.dnguy38.lastplayed.data.search.AlbumSearchResults
import com.dnguy38.lastplayed.LastFmApplication.Companion as Application
import com.dnguy38.lastplayed.data.search.SearchResults
import com.dnguy38.lastplayed.data.search.SearchType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG = "com.dnguy38.lastplayed.ui.search.SearchViewModel"

class SearchViewModel : ViewModel() {
    private val _searchResults = MutableLiveData<SearchResults>()
    val searchResults: LiveData<SearchResults> = _searchResults

    fun search(query: String, type: SearchType) {
        val apiKey = Application.instance.sharedPreferences.getString("api_key", null)
            ?: throw IllegalStateException("API key not found in shared preferences")

        when (type) {
            SearchType.Album -> searchAlbum(query, apiKey)
            SearchType.Artist -> searchArtist(query, apiKey)
            SearchType.Track -> searchTrack(query, apiKey)
        }
    }

    fun searchAlbum(query: String, apiKey: String) {
        val albumSearchRequest = Application.api.albumSearch(null, null, query, apiKey)

        albumSearchRequest.enqueue(object : Callback<AlbumSearchResponse> {
            override fun onResponse(
                call: Call<AlbumSearchResponse>,
                response: Response<AlbumSearchResponse>
            ) {
                response.body()?.let {
                    _searchResults.value = AlbumSearchResults(it.results.matches)
                }
            }

            override fun onFailure(call: Call<AlbumSearchResponse>, t: Throwable) {
                Log.d(TAG, "Failed to search for album")
            }
        })
    }

    fun searchArtist(query: String, apiKey: String) {
        TODO()
    }

    fun searchTrack(query: String, apiKey: String) {
        TODO()
    }
}