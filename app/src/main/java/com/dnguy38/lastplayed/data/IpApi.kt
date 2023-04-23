package com.dnguy38.lastplayed.data

import retrofit2.Call
import retrofit2.http.GET

interface IpApi {
    @GET("/json")
    fun get(): Call<IpResponse>
}