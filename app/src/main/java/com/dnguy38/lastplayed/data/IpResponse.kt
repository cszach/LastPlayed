package com.dnguy38.lastplayed.data

import com.google.gson.annotations.SerializedName

data class IpResponse(
    val status: String,
    val message: String?,
    val country: String,
    val countryCode: String,
    val region: String,
    val regionName: String,
    val city: String,
    val zip: String,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val isp: String,
    val org: String,
    @SerializedName("as") val asNumber: String,
    val query: String
)
