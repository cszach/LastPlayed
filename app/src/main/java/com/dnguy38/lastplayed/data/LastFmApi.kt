package com.dnguy38.lastplayed.data

import com.dnguy38.lastplayed.data.last_fm.MobileSessionResponse
import com.dnguy38.lastplayed.data.last_fm.TopTracksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LastFmApi {
    @POST("?method=auth.getMobileSession&format=json")
    fun getMobileSession(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("api_key") apiKey: String,
        @Query("api_sig") apiSig: String
    ): Call<MobileSessionResponse>

    @GET("?method=geo.gettoptracks&format=json")
    fun getTopTracks(
        @Query("country") country: String,
        @Query("location") location: String?,
        @Query("limit") resultsPerPage: Int?,
        @Query("page") pageNumber: Int?,
        @Query("api_key") apiKey: String
    ): Call<TopTracksResponse>
}