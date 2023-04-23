package com.dnguy38.lastplayed.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnguy38.lastplayed.data.IpApi
import com.dnguy38.lastplayed.data.last_fm.responses.TopArtist
import com.dnguy38.lastplayed.data.last_fm.responses.TopTracksResponse
import com.dnguy38.lastplayed.data.last_fm.responses.TopArtistsResponse
import com.dnguy38.lastplayed.data.last_fm.responses.TopTrack
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import com.dnguy38.lastplayed.LastFmApplication.Companion as Application

private val TAG = "com.dnguy38.lastplayed.ui.home.HomeViewModel"

class HomeViewModel : ViewModel() {
    private val _topTracks = MutableLiveData<List<TopTrack>>()
    val topTracks: LiveData<List<TopTrack>> = _topTracks

    private val _topArtists = MutableLiveData<List<TopArtist>>()
    val topArtists: LiveData<List<TopArtist>> = _topArtists

    lateinit var country: String

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ip-api.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val ipApi: IpApi = retrofit.create()

        val ipRequest = ipApi.get()
        val networkThread = Thread() {
            val ipResponse = ipRequest.execute()

            if (ipResponse.isSuccessful) {
                ipResponse.body()?.let {
                    country = it.country

                    Log.d(TAG, it.toString())
                }
            } else {
                // TODO: Add default country to string resource
                Log.d(TAG, "Failed to detect country, falling back to United States")
                country = "United States"
            }
        }

        networkThread.start()
        networkThread.join()
    }

    fun getTopTracks() {

        val apiKey = Application.instance.sharedPreferences.getString("api_key", null)
            ?: throw IllegalStateException("API key not found in shared preferences")

        val topTracksRequest = Application.api.geoGetTopTracks(country, null, 10, 1, apiKey)
        val topArtistsRequest = Application.api.geoGetTopArtists(country, 10, null, apiKey)

        topTracksRequest.enqueue(object : Callback<TopTracksResponse> {
            override fun onResponse(
                call: Call<TopTracksResponse>,
                response: Response<TopTracksResponse>
            ) {
                response.body()?.let {
                    _topTracks.value = it.tracks.track

                    Log.d(TAG, it.toString())
                }
            }

            override fun onFailure(call: Call<TopTracksResponse>, t: Throwable) {
                Log.d(TAG, "Failed to get top tracks")
            }
        })

        topArtistsRequest.enqueue(object : Callback<TopArtistsResponse> {
            override fun onResponse(
                call: Call<TopArtistsResponse>,
                response: Response<TopArtistsResponse>
            ) {
                response.body()?.let {
                    _topArtists.value = it.topArtists.artist

                    Log.d(TAG, it.toString())
                }
            }

            override fun onFailure(call: Call<TopArtistsResponse>, t: Throwable) {
                Log.d(TAG, "Failed to get top artists")
            }
        })
    }
}