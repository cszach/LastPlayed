package com.dnguy38.lastplayed.data

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface LastFmApi {
    @POST("?method=auth.getMobileSession")
    fun getMobileSession(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("api_key") apiKey: String,
        @Query("api_sig") apiSig: String
    ): Call<MobileSessionResponse>
}