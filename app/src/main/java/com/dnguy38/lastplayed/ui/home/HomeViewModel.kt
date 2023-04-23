package com.dnguy38.lastplayed.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnguy38.lastplayed.data.last_fm.TopTrack
import com.dnguy38.lastplayed.data.last_fm.TopTracksResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.dnguy38.lastplayed.LastFmApplication.Companion as Application

private val TAG = "com.dnguy38.lastplayed.ui.home.HomeViewModel"

class HomeViewModel : ViewModel() {
    private val _topTracks = MutableLiveData<List<TopTrack>>()
    val topTracks: LiveData<List<TopTrack>> = _topTracks

    fun getTopTracks() {

        val apiKey = Application.instance.sharedPreferences.getString("api_key", null)
            ?: throw IllegalStateException("API key not found in shared preferences")

        val topTracksRequest = Application.api.getTopTracks("United States", null, 10, 1, apiKey)

        topTracksRequest.enqueue(object : Callback<TopTracksResponse> {
            override fun onResponse(
                call: Call<TopTracksResponse>,
                response: Response<TopTracksResponse>
            ) {
                response.body()?.let {
                    _topTracks.value = it.tracks.track
                }
            }

            override fun onFailure(call: Call<TopTracksResponse>, t: Throwable) {
                Log.d(TAG, "Failed to get top tracks")
            }
        })
    }
}