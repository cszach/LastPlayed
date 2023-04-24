package com.dnguy38.lastplayed.data

import com.dnguy38.lastplayed.data.last_fm.MobileSessionResponse
import com.dnguy38.lastplayed.data.last_fm.responses.*
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
    fun geoGetTopTracks(
        @Query("country") country: String,
        @Query("location") location: String?,
        @Query("limit") resultsPerPage: Int?,
        @Query("page") pageNumber: Int?,
        @Query("api_key") apiKey: String
    ): Call<TopTracksResponse>

    @GET("?method=geo.gettopartists&format=json")
    fun geoGetTopArtists(
        @Query("country") country: String,
        @Query("limit") limit: Int?,
        @Query("page") page: Int?,
        @Query("api_key") apiKey: String
    ): Call<TopArtistsResponse>

    @GET("?method=album.search&format=json")
    fun albumSearch(
        @Query("limit") limit: Int?,
        @Query("page") page: Int?,
        @Query("album") album: String,
        @Query("api_key") apiKey: String
    ): Call<AlbumSearchResponse>

    @GET("?method=artist.search&format=json")
    fun artistSearch(
        @Query("limit") limit: Int?,
        @Query("page") page: Int?,
        @Query("artist") artist: String,
        @Query("api_key") apiKey: String
    ): Call<ArtistSearchResponse>

    @GET("?method=track.search&format=json")
    fun trackSearch(
        @Query("limit") limit: Int?,
        @Query("page") page: Int?,
        @Query("track") track: String,
        @Query("artist") artist: String?,
        @Query("api_key") apiKey: String
    ): Call<TrackSearchResponse>
}