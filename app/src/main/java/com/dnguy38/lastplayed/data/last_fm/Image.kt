package com.dnguy38.lastplayed.data.last_fm

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text") val url: String,
    val size: String
)