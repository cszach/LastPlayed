package com.dnguy38.lastplayed.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnguy38.lastplayed.data.last_fm.responses.*
import com.dnguy38.lastplayed.LastFmApplication.Companion as Application
import com.dnguy38.lastplayed.data.search.SearchResults
import com.dnguy38.lastplayed.data.search.SearchType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

private val TAG = "com.dnguy38.lastplayed.ui.search.SearchViewModel"

class SearchViewModel : ViewModel() {
    private val _searchResults = MutableLiveData<SearchResults>()
    val searchResults: LiveData<SearchResults> = _searchResults

    fun search(query: String, limit: Int, type: SearchType) {
        val apiKey = Application.instance.sharedPreferences.getString("api_key", null)
            ?: throw IllegalStateException("API key not found in shared preferences")

        when (type) {
            SearchType.Album -> searchAlbum(query, limit, apiKey)
            SearchType.Artist -> searchArtist(query, limit, apiKey)
            SearchType.Track -> searchTrack(query, limit, apiKey)
        }
    }

    private fun searchAlbum(query: String, limit: Int, apiKey: String) {
        val albumSearchRequest = Application.api.albumSearch(limit, null, query, apiKey)

        albumSearchRequest.enqueue(object : Callback<AlbumSearchResponse> {
            override fun onResponse(
                call: Call<AlbumSearchResponse>,
                response: Response<AlbumSearchResponse>
            ) {
                response.body()?.let {
                    if (it.results != null) {
                        _searchResults.value = it.results!!
                    } else {
                        _searchResults.value = AlbumSearchResults(AlbumMatches(emptyList()))
                    }

                    Log.d(TAG, response.toString())
                }
            }

            override fun onFailure(call: Call<AlbumSearchResponse>, t: Throwable) {
                // TODO: Display a toast instead?
                Log.d(TAG, "Failed to search for album")
            }
        })
    }

    private fun searchArtist(query: String, limit: Int, apiKey: String) {
        val artistSearchRequest = Application.api.artistSearch(limit, null, query, apiKey)

        artistSearchRequest.enqueue(object : Callback<ArtistSearchResponse> {
            override fun onResponse(
                call: Call<ArtistSearchResponse>,
                response: Response<ArtistSearchResponse>
            ) {
                response.body()?.let {
                    if (it.results != null) {
                        _searchResults.value = it.results!!
                    } else {
                        _searchResults.value = ArtistSearchResults(ArtistMatches(emptyList()))
                    }

                    Log.d(TAG, response.toString())
                }
            }

            override fun onFailure(call: Call<ArtistSearchResponse>, t: Throwable) {
                Log.d(TAG, "Failed to search for artist")
            }

        })
    }

    private fun searchTrack(query: String, limit: Int, apiKey: String) {
        val trackSearchRequest = Application.api.trackSearch(limit, null, query, null, apiKey)

        trackSearchRequest.enqueue(object : Callback<TrackSearchResponse> {
            override fun onResponse(
                call: Call<TrackSearchResponse>,
                response: Response<TrackSearchResponse>
            ) {
                response.body()?.let {
                    if (it.results != null) {
                        _searchResults.value = it.results!!
                    } else {
                        _searchResults.value = TrackSearchResults(TrackMatches(emptyList()))
                    }

                    Log.d(TAG, response.toString())
                }
            }

            override fun onFailure(call: Call<TrackSearchResponse>, t: Throwable) {
                Log.d(TAG, "Failed to search for track")

                throw IOException("Failed to search for track", t)
            }
        })
    }
}