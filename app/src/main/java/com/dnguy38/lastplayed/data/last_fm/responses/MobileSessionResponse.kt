package com.dnguy38.lastplayed.data.last_fm

data class MobileSession(
    val name: String,
    val key: String,
    val subscriber: Int
)

data class MobileSessionResponse(
    val session: MobileSession
)